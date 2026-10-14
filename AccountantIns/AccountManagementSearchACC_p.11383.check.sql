-- Run after deploying AccountManagementSearchACC_p.simplified.sql
-- in the database where account 11383 has two matching policies.
USE [accinsurancedb]
GO

CREATE TABLE #SearchResults
(
    SN INT,
    AccountNumber INT,
    AccountID INT,
    AccountName VARCHAR(100),
    AccountEmail VARCHAR(500),
    WorkPhone VARCHAR(100),
    PolicyEffectiveDate DATETIME,
    State VARCHAR(100)
);

INSERT INTO #SearchResults
EXEC dbo.AccountManagementSearchACC_p NULL, '11383', NULL, NULL, NULL, NULL;

SELECT * FROM #SearchResults ORDER BY SN;

IF (SELECT COUNT(*) FROM #SearchResults) <> 2
    RAISERROR ('Account 11383 should return two rows.', 16, 1);

TRUNCATE TABLE #SearchResults;

INSERT INTO #SearchResults
EXEC dbo.AccountManagementSearchACC_p 'CPA', '', NULL, NULL, NULL, NULL;

IF EXISTS (SELECT 1 FROM #SearchResults GROUP BY AccountID HAVING COUNT(*) > 1)
    RAISERROR ('CPA search should return each account once.', 16, 1);

DROP TABLE #SearchResults;
GO
