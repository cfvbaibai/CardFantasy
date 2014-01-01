IF dbo.fn_util_table_exists('tbl_stat_boss_battle') = 0
BEGIN

CREATE TABLE [dbo].[tbl_stat_boss_battle](
	[i_id] INT NOT NULL,
	[dt_created] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
	[nvc_boss_name] [nvarchar](10) NOT NULL,
	[bi_battle_count] BIGINT NOT NULL,
	[i_hero_lv] INT NOT NULL,
	[bi_min_damage] BIGINT NOT NULL,
	[bi_avg_damage] BIGINT NOT NULL,
	[bi_max_damage] BIGINT NOT NULL,
	[nvc_deck] [nvarchar](256) NOT NULL
)

CREATE UNIQUE CLUSTERED INDEX CI_PRIMARY ON tbl_stat_boss_battle (
	nvc_boss_name,
	i_hero_lv,
	nvc_deck)

CREATE NONCLUSTERED INDEX IX_AVG_DAMAGE ON tbl_stat_boss_battle (
	nvc_boss_name,
	i_hero_lv,
	bi_avg_damage DESC
)

CREATE NONCLUSTERED INDEX IX_MIN_DAMAGE ON tbl_stat_boss_battle (
	nvc_boss_name,
	i_hero_lv,
	bi_min_damage DESC
)

CREATE NONCLUSTERED INDEX IX_MAX_DAMAGE ON tbl_stat_boss_battle (
	nvc_boss_name,
	i_hero_lv,
	bi_max_damage DESC
)

END

GO