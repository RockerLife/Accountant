USE [accinsurancedb]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- SQL Server 2008-compatible; @AccountNumber accepts comma-separated IDs.
ALTER PROCEDURE [dbo].[AccountManagementSearchACC_p]
    @AccountName VARCHAR(50),
    @AccountNumber VARCHAR(100),
    @EmailId VARCHAR(150),
    @PolicyEffectiveMonthYear VARCHAR(10),
    @PhoneNumber VARCHAR(100),
    @StateCode VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    SELECT
        @AccountName = NULLIF(LTRIM(RTRIM(@AccountName)), ''),
        @AccountNumber = NULLIF(LTRIM(RTRIM(@AccountNumber)), ''),
        @EmailId = NULLIF(LTRIM(RTRIM(@EmailId)), ''),
        @PolicyEffectiveMonthYear = NULLIF(LTRIM(RTRIM(@PolicyEffectiveMonthYear)), ''),
        @PhoneNumber = NULLIF(LTRIM(RTRIM(@PhoneNumber)), ''),
        @StateCode = NULLIF(LTRIM(RTRIM(@StateCode)), '');

    DECLARE @AccountNumbers TABLE (AccountID INT NOT NULL PRIMARY KEY);
    DECLARE @Remaining VARCHAR(101) = COALESCE(@AccountNumber, '') + ',';
    DECLARE @Token VARCHAR(100);
    DECLARE @Comma INT;
    DECLARE @ID BIGINT;

    WHILE LEN(@Remaining) > 1
    BEGIN
        SET @Comma = CHARINDEX(',', @Remaining);
        SET @Token = LTRIM(RTRIM(LEFT(@Remaining, @Comma - 1)));

        IF LEN(@Token) BETWEEN 1 AND 10
            AND @Token COLLATE Latin1_General_BIN2 NOT LIKE '%[^0-9]%'
        BEGIN
            SET @ID = CONVERT(BIGINT, @Token);
            IF @ID <= 2147483647
                INSERT INTO @AccountNumbers (AccountID)
                SELECT CONVERT(INT, @ID)
                WHERE NOT EXISTS
                    (SELECT 1 FROM @AccountNumbers WHERE AccountID = @ID);
        END;

        SET @Remaining = SUBSTRING(@Remaining, @Comma + 1, 101);
    END;

    DECLARE @EffectiveStartDate DATE;
    DECLARE @EffectiveEndDate DATE;
    DECLARE @EffectiveDateText CHAR(8);
    DECLARE @SlashIndex INT = CHARINDEX('/', @PolicyEffectiveMonthYear);

    IF @PolicyEffectiveMonthYear IS NOT NULL AND @SlashIndex > 0
    BEGIN
        SET @EffectiveDateText =
            RIGHT('0000' + LTRIM(RTRIM(SUBSTRING(@PolicyEffectiveMonthYear, @SlashIndex + 1, 4))), 4)
            + RIGHT('0' + LTRIM(RTRIM(LEFT(@PolicyEffectiveMonthYear, @SlashIndex - 1))), 2)
            + '01';

        IF ISDATE(@EffectiveDateText) = 1
        BEGIN
            SET @EffectiveStartDate = CONVERT(DATE, @EffectiveDateText, 112);
            SET @EffectiveEndDate = DATEADD(MONTH, 1, @EffectiveStartDate);
        END;
    END;

    ;WITH AccountMatches AS
    (
        SELECT
            p.PolicyKey,
            p.AccountID,
            CAST(LTRIM(RTRIM(UPPER(a.AccountName))) AS VARCHAR(100)) AS AccountName,
            CAST(a.AccountEmail AS VARCHAR(500)) AS AccountEmail,
            CAST(ad.WorkPhone AS VARCHAR(100)) AS WorkPhone,
            p.PolicyEffectiveDate,
            CAST(st.StateDesc AS VARCHAR(100)) AS State,
            -- Name and other broad searches list accounts; number searches list policies.
            ROW_NUMBER() OVER
            (
                PARTITION BY CASE WHEN @AccountNumber IS NULL THEN p.AccountID ELSE p.PolicyKey END
                ORDER BY p.PolicyEffectiveDate DESC, p.PolicyKey DESC, aa.AccountAddressKey
            ) AS SearchRow
        FROM dbo.Policy p
        CROSS APPLY
        (
            SELECT TOP (1) pt.AccountKey
            FROM dbo.PolicyTransaction pt WITH (NOLOCK)
            WHERE pt.PolicyKey = p.PolicyKey
            ORDER BY pt.TransactionSequence
        ) pt
        INNER JOIN dbo.Account a WITH (NOLOCK) ON a.AccountKey = pt.AccountKey
        INNER JOIN dbo.AccountAddress aa WITH (NOLOCK) ON aa.AccountKey = pt.AccountKey
        INNER JOIN dbo.Address ad WITH (NOLOCK) ON ad.AddressKey = aa.AddressKey
        LEFT JOIN dbo.State st WITH (NOLOCK) ON st.StateCode = ad.StateCode
        WHERE p.MarketableProductKey = 1
            AND p.isUncompletedData <> 'Y'
            AND EXISTS (SELECT 1 FROM dbo.Firm f WITH (NOLOCK) WHERE f.PolicyKey = p.PolicyKey)
            AND (@AccountName IS NULL OR a.AccountName LIKE @AccountName + '%')
            AND (@EmailId IS NULL OR a.AccountEmail LIKE @EmailId + '%')
            AND (@AccountNumber IS NULL OR EXISTS
                (SELECT 1 FROM @AccountNumbers n WHERE n.AccountID = p.AccountID))
            AND (@PhoneNumber IS NULL OR ad.WorkPhone = @PhoneNumber)
            AND (@StateCode IS NULL OR ad.StateCode = @StateCode)
            AND (@PolicyEffectiveMonthYear IS NULL OR
                (p.PolicyEffectiveDate >= @EffectiveStartDate
                 AND p.PolicyEffectiveDate < @EffectiveEndDate))
    )
    SELECT
        CAST(ROW_NUMBER() OVER
            (ORDER BY AccountName, AccountID, PolicyEffectiveDate DESC, PolicyKey DESC) + 9 AS INT) AS SN,
        AccountID AS AccountNumber,
        AccountID,
        AccountName,
        AccountEmail,
        WorkPhone,
        PolicyEffectiveDate,
        State
    FROM AccountMatches
    WHERE SearchRow = 1
    ORDER BY SN
    OPTION (RECOMPILE);
END;
GO
