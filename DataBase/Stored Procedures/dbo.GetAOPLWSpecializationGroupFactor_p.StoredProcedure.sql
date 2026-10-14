USE [accinsurancedb]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

IF OBJECT_ID(N'[dbo].[GetAOPLWSpecializationGroupFactor_p]', N'P') IS NULL
    EXEC(N'CREATE PROCEDURE [dbo].[GetAOPLWSpecializationGroupFactor_p] AS BEGIN SET NOCOUNT ON; END')
GO

ALTER PROCEDURE [dbo].[GetAOPLWSpecializationGroupFactor_p]
(
    @PolicyKey [int],
    @VersionKey [int]
)
AS
BEGIN
    SET NOCOUNT ON

    DECLARE @IsFullQuote [char](1)
    DECLARE @IsQuickQuote [char](1)
    DECLARE @PolicyEffectiveDate [datetime]
    DECLARE @StateCode [varchar](20)
    DECLARE @SpecializationGroupCount [int]
    DECLARE @SpecializationGroupFactor [decimal](4,2)

    SELECT       @IsFullQuote = [p].[IsFullQuote],@IsQuickQuote = [p].[IsQuickQuote],@PolicyEffectiveDate = [p].[PolicyEffectiveDate]   FROM [dbo].[Policy] AS [p]
    WHERE [p].[PolicyKey] = @PolicyKey

    SELECT TOP (1)   @StateCode = UPPER(LTRIM(RTRIM([ad].[StateCode])))
    FROM [dbo].[PolicyTransaction] AS [pt]
    INNER JOIN [dbo].[AccountAddress] AS [aa]  ON [aa].[AccountKey] = [pt].[AccountKey]
    INNER JOIN [dbo].[Address] AS [ad]  ON [ad].[AddressKey] = [aa].[AddressKey]
    WHERE [pt].[PolicyKey] = @PolicyKey
    ORDER BY [pt].[TransactionSequence] DESC, [ad].[AddressKey] DESC

    IF @PolicyEffectiveDate IS NULL
       OR @PolicyEffectiveDate < CONVERT([datetime], '20260901', 112)
       OR ISNULL(@StateCode, '') NOT IN
    (
        'AZ', 'IL', 'IN', 'KS', 'MI', 'MN', 'NV', 'NC', 'OH', 'PA', 'TX', 'WI', 'CT', 'FL', 'GA', 'TN', 'UT'
    )
    BEGIN
        SELECT 0 AS [AOPSpecializationGroupFactor]
        RETURN
    END

    IF @IsFullQuote = 'Y'
    BEGIN
        SELECT @SpecializationGroupCount = COUNT(DISTINCT [sg].[AOPSpecilizationGroupKey])
        FROM [dbo].[AreaOfPracticeLW] AS [ap]
        INNER JOIN [dbo].[AOPLW] AS [aop] ON [aop].[AOPKey] = [ap].[AOPKey]
        INNER JOIN [dbo].[AOPLWSpecilizationGroup] AS [sg] ON [sg].[AOPSpecilizationGroupKey] = [aop].[AOPSpecilizationGroupKey]
        WHERE [ap].[PolicyKey] = @PolicyKey AND [ap].[VersionKey] = @VersionKey AND [ap].[PercentageValue] > 0
    END
    ELSE IF @IsQuickQuote = 'Y'
    BEGIN
        SELECT @SpecializationGroupCount = COUNT(DISTINCT [sg].[AOPSpecilizationGroupKey])
        FROM [dbo].[AreaOfPracticeQuickQuoteLW] AS [ap]
        INNER JOIN [dbo].[AOPLW] AS [aop] ON [aop].[AOPKey] = [ap].[AOPKey]
        INNER JOIN [dbo].[AOPLWSpecilizationGroup] AS [sg] ON [sg].[AOPSpecilizationGroupKey] = [aop].[AOPSpecilizationGroupKey]
        WHERE [ap].[PolicyKey] = @PolicyKey  AND [ap].[VersionKey] = @VersionKey AND [ap].[PercentageValue] > 0
    END

    SELECT  @SpecializationGroupFactor = [f].[Factor]
    FROM [dbo].[AOPLWSpecializationGroupFactor] AS [f]
    WHERE [f].[MinimumSpecializationGroupCount] =  CASE WHEN @SpecializationGroupCount >= 5 THEN 5 ELSE @SpecializationGroupCount END

    SELECT @SpecializationGroupFactor AS [AOPSpecializationGroupFactor]
END
GO
