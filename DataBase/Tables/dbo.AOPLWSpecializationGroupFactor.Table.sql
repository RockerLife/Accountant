USE [accinsurancedb]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

IF OBJECT_ID(N'[dbo].[AOPLWSpecializationGroupFactor]', N'U') IS NULL
BEGIN
    CREATE TABLE [dbo].[AOPLWSpecializationGroupFactor]
    (
        [MinimumSpecializationGroupCount] [tinyint] NOT NULL,
        [Factor] [decimal](4,2) NOT NULL,
        CONSTRAINT [PK_AOPLWSpecializationGroupFactor] PRIMARY KEY CLUSTERED
        (
            [MinimumSpecializationGroupCount] ASC
        ),
        CONSTRAINT [CK_AOPLWSpecializationGroupFactor_MinimumCount]
            CHECK ([MinimumSpecializationGroupCount] > 0),
        CONSTRAINT [CK_AOPLWSpecializationGroupFactor_Factor]
            CHECK ([Factor] > 0)
    ) ON [PRIMARY]
END
GO

INSERT INTO [dbo].[AOPLWSpecializationGroupFactor]
(
    [MinimumSpecializationGroupCount],
    [Factor]
)
SELECT
    [Source].[MinimumSpecializationGroupCount],
    [Source].[Factor]
FROM
(
    SELECT CAST(1 AS [tinyint]) AS [MinimumSpecializationGroupCount], CAST(0.90 AS [decimal](4,2)) AS [Factor]
    UNION ALL SELECT 2, 0.95
    UNION ALL SELECT 3, 1.00
    UNION ALL SELECT 4, 1.05
    UNION ALL SELECT 5, 1.10
) AS [Source]
WHERE NOT EXISTS
(
    SELECT 1
    FROM [dbo].[AOPLWSpecializationGroupFactor] AS [Target]
    WHERE [Target].[MinimumSpecializationGroupCount] = [Source].[MinimumSpecializationGroupCount]
)
GO
