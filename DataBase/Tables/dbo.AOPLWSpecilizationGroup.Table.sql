USE [accinsurancedb]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

IF OBJECT_ID(N'[dbo].[AOPLWSpecilizationGroup]', N'U') IS NULL
BEGIN
    CREATE TABLE [dbo].[AOPLWSpecilizationGroup]
    (
        [AOPSpecilizationGroupKey] [int] IDENTITY(1,1) NOT NULL,
        [AOPSpecilizationGroupDesc] [varchar](50) NOT NULL,
        CONSTRAINT [PK_AOPLWSpecilizationGroup] PRIMARY KEY CLUSTERED
        (
            [AOPSpecilizationGroupKey] ASC
        )
    ) ON [PRIMARY]
END
GO

INSERT INTO [dbo].[AOPLWSpecilizationGroup] ([AOPSpecilizationGroupDesc])
SELECT [Source].[AOPSpecilizationGroupDesc]
FROM
(
    SELECT 'Administrative' AS [AOPSpecilizationGroupDesc]
    UNION ALL SELECT 'Collections'
    UNION ALL SELECT 'Corporate/Commercial/Business'
    UNION ALL SELECT 'Defense Litigation'
    UNION ALL SELECT 'Employment/Civil Rights'
    UNION ALL SELECT 'Environmental'
    UNION ALL SELECT 'Family'
    UNION ALL SELECT 'Financial Services'
    UNION ALL SELECT 'Intellectual Propery/Media'
    UNION ALL SELECT 'International'
    UNION ALL SELECT 'Other'
    UNION ALL SELECT 'Plaintiff Litigation'
    UNION ALL SELECT 'Real Estate'
    UNION ALL SELECT 'Tax'
    UNION ALL SELECT 'Transportation/Infustructure'
    UNION ALL SELECT 'Trusts/Wills/Estates'
) AS [Source]
WHERE NOT EXISTS
(
    SELECT 1
    FROM [dbo].[AOPLWSpecilizationGroup] AS [Target]
    WHERE [Target].[AOPSpecilizationGroupDesc] = [Source].[AOPSpecilizationGroupDesc]
)
GO

IF COL_LENGTH(N'dbo.AOPLW', N'AOPSpecilizationGroupKey') IS NULL
    ALTER TABLE [dbo].[AOPLW] ADD [AOPSpecilizationGroupKey] [int] NULL
GO

UPDATE [AOP]
SET [AOP].[AOPSpecilizationGroupKey] = [SpecializationGroup].[AOPSpecilizationGroupKey]
FROM [dbo].[AOPLW] AS [AOP]
INNER JOIN
(
    SELECT 'Administrative or Regulatory' AS [AOPDesc], 'Administrative' AS [GroupDesc]
    UNION ALL SELECT 'Gov''t/Municipal/Zoning', 'Administrative'
    UNION ALL SELECT 'Immigration', 'Administrative'
    UNION ALL SELECT 'Education', 'Administrative'
    UNION ALL SELECT 'Social Security/Disability', 'Administrative'
    UNION ALL SELECT 'Utilities', 'Administrative'

    UNION ALL SELECT 'Collections/Repossession', 'Collections'
    UNION ALL SELECT 'Creditor Debtor Rights', 'Collections'
    UNION ALL SELECT 'Bankruptcy/Insolvency and Reorganization', 'Collections'

    UNION ALL SELECT 'Corporate/Commercial/Business', 'Corporate/Commercial/Business'
    UNION ALL SELECT 'Construction', 'Corporate/Commercial/Business'
    UNION ALL SELECT 'Healthcare', 'Corporate/Commercial/Business'
    UNION ALL SELECT 'Mergers and Acquisitions', 'Corporate/Commercial/Business'
    UNION ALL SELECT 'Non-Profit/Charities', 'Corporate/Commercial/Business'

    UNION ALL SELECT 'Alternate Dispute Resolution', 'Defense Litigation'
    UNION ALL SELECT 'Appellate Practice', 'Defense Litigation'
    UNION ALL SELECT 'Civil Litigation - Defense', 'Defense Litigation'
    UNION ALL SELECT 'Construction Litigation - Defense', 'Defense Litigation'
    UNION ALL SELECT 'Corporate/Commercial/Business Litigation - Defense', 'Defense Litigation'
    UNION ALL SELECT 'Criminal Defense', 'Defense Litigation'
    UNION ALL SELECT 'Insurance Defense', 'Defense Litigation'
    UNION ALL SELECT 'Litigation Other - Defense', 'Defense Litigation'
    UNION ALL SELECT 'Mass Tort/Class Action Litigation - Defense', 'Defense Litigation'
    UNION ALL SELECT 'Medical Malpractice - Defense', 'Defense Litigation'
    UNION ALL SELECT 'Non-Medical Malpractice - Defense', 'Defense Litigation'
    UNION ALL SELECT 'Personal Injury Litigation - Defense', 'Defense Litigation'
    UNION ALL SELECT 'Product Liability Litigation - Defense', 'Defense Litigation'
    UNION ALL SELECT 'Traffic or DUI/DWI Defense', 'Defense Litigation'
    UNION ALL SELECT 'Workers Compensation - Defense', 'Defense Litigation'

    UNION ALL SELECT 'Civil Rights or Discrimination', 'Employment/Civil Rights'
    UNION ALL SELECT 'Employee Benefits (ERISA)', 'Employment/Civil Rights'
    UNION ALL SELECT 'Employment', 'Employment/Civil Rights'
    UNION ALL SELECT 'Labor Management', 'Employment/Civil Rights'
    UNION ALL SELECT 'Labor Union', 'Employment/Civil Rights'

    UNION ALL SELECT 'Environmental', 'Environmental'
    UNION ALL SELECT 'Natural Resources', 'Environmental'

    UNION ALL SELECT 'Family Law', 'Family'
    UNION ALL SELECT 'Juvenile/Guardianship', 'Family'

    UNION ALL SELECT 'Financial Institution/Banking/Finance', 'Financial Services'
    UNION ALL SELECT 'Investment Counseling/Money Management', 'Financial Services'
    UNION ALL SELECT 'Securities and/or Securities Litigation', 'Financial Services'
    UNION ALL SELECT 'Antitrust', 'Financial Services'

    UNION ALL SELECT 'International', 'International'

    UNION ALL SELECT 'Copyright/Trademark', 'Intellectual Propery/Media'
    UNION ALL SELECT 'Patent', 'Intellectual Propery/Media'
    UNION ALL SELECT 'Entertainment/Sports', 'Intellectual Propery/Media'
    UNION ALL SELECT 'Communications/Media', 'Intellectual Propery/Media'

    UNION ALL SELECT 'Civil Litigation - Plaintiff', 'Plaintiff Litigation'
    UNION ALL SELECT 'Construction Litigation - Plaintiff', 'Plaintiff Litigation'
    UNION ALL SELECT 'Corporate/Commercial/Business Litigation - Plaintiff', 'Plaintiff Litigation'
    UNION ALL SELECT 'Litigation Other - Plaintiff', 'Plaintiff Litigation'
    UNION ALL SELECT 'Mass Tort/Class Action Litigation - Plaintiff', 'Plaintiff Litigation'
    UNION ALL SELECT 'Medical Malpractice - Plaintiff', 'Plaintiff Litigation'
    UNION ALL SELECT 'Non-Medical Malpractice - Plaintiff', 'Plaintiff Litigation'
    UNION ALL SELECT 'Personal Injury Litigation - Plaintiff', 'Plaintiff Litigation'
    UNION ALL SELECT 'Product Liability Litigation - Plaintiff', 'Plaintiff Litigation'
    UNION ALL SELECT 'Workers Compensation - Plaintiff', 'Plaintiff Litigation'

    UNION ALL SELECT 'Real Estate Residential', 'Real Estate'
    UNION ALL SELECT 'Real Estate Commercial', 'Real Estate'

    UNION ALL SELECT 'Tax', 'Tax'

    UNION ALL SELECT 'Admiralty and Maritime', 'Transportation/Infustructure'
    UNION ALL SELECT 'Aviation', 'Transportation/Infustructure'

    UNION ALL SELECT 'Elder Law', 'Trusts/Wills/Estates'
    UNION ALL SELECT 'Trusts/Estates/Wills/Probate', 'Trusts/Wills/Estates'

    UNION ALL SELECT 'Other', 'Other'
) AS [Mapping]
    ON [Mapping].[AOPDesc] = LTRIM(RTRIM(COALESCE(NULLIF([AOP].[AOPDescNew], ''), [AOP].[AOPDesc])))
INNER JOIN [dbo].[AOPLWSpecilizationGroup] AS [SpecializationGroup]
    ON [SpecializationGroup].[AOPSpecilizationGroupDesc] = [Mapping].[GroupDesc]
WHERE [AOP].[AOPSpecilizationGroupKey] IS NULL
   OR [AOP].[AOPSpecilizationGroupKey] <> [SpecializationGroup].[AOPSpecilizationGroupKey]
GO

UPDATE [AOP]
SET [AOP].[AOPSpecilizationGroupKey] = [CorporateGroup].[AOPSpecilizationGroupKey]
FROM [dbo].[AOPLW] AS [AOP]
INNER JOIN [dbo].[AOPLWSpecilizationGroup] AS [HealthcareGroup]
    ON [HealthcareGroup].[AOPSpecilizationGroupKey] = [AOP].[AOPSpecilizationGroupKey]
   AND [HealthcareGroup].[AOPSpecilizationGroupDesc] = 'Healthcare'
INNER JOIN [dbo].[AOPLWSpecilizationGroup] AS [CorporateGroup]
    ON [CorporateGroup].[AOPSpecilizationGroupDesc] = 'Corporate/Commercial/Business'
GO

DELETE FROM [dbo].[AOPLWSpecilizationGroup]
WHERE [AOPSpecilizationGroupDesc] = 'Healthcare'
GO

IF NOT EXISTS
(
    SELECT 1
    FROM sys.foreign_keys
    WHERE [name] = N'FK_AOPLW_AOPLWSpecilizationGroup'
      AND [parent_object_id] = OBJECT_ID(N'[dbo].[AOPLW]')
)
BEGIN
    ALTER TABLE [dbo].[AOPLW] WITH CHECK ADD CONSTRAINT [FK_AOPLW_AOPLWSpecilizationGroup]
        FOREIGN KEY ([AOPSpecilizationGroupKey])
        REFERENCES [dbo].[AOPLWSpecilizationGroup] ([AOPSpecilizationGroupKey])
END
GO




update AOPLW set AOPSpecilizationGroupKey=8 where AOPKey=1
update AOPLW set AOPSpecilizationGroupKey=2 where AOPKey=3
update AOPLW set AOPSpecilizationGroupKey=3 where AOPKey=5
update AOPLW set AOPSpecilizationGroupKey=9 where AOPKey=6
update AOPLW set AOPSpecilizationGroupKey=8 where AOPKey=12
update AOPLW set AOPSpecilizationGroupKey=1 where AOPKey=13
update AOPLW set AOPSpecilizationGroupKey=8 where AOPKey=15
update AOPLW set AOPSpecilizationGroupKey=12 where AOPKey=18
update AOPLW set AOPSpecilizationGroupKey=8 where AOPKey=21
update AOPLW set AOPSpecilizationGroupKey=16 where AOPKey=24
update AOPLW set AOPSpecilizationGroupKey=2 where AOPKey=31
update AOPLW set AOPSpecilizationGroupKey=9 where AOPKey=32
update AOPLW set AOPSpecilizationGroupKey=9 where AOPKey=38
update AOPLW set AOPSpecilizationGroupKey=7 where AOPKey=43
update AOPLW set AOPSpecilizationGroupKey=3 where AOPKey=47
update AOPLW set AOPSpecilizationGroupKey=1 where AOPKey=49
update AOPLW set AOPSpecilizationGroupKey=12 where AOPKey in(53,57,59,67)
update AOPLW set AOPSpecilizationGroupKey=4 where AOPKey in(52,58,66,68)
