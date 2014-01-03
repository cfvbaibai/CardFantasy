IF dbo.fn_util_sp_exists('sp_stat_recommend_boss_battle_deck') = 1 DROP PROCEDURE sp_stat_recommend_boss_battle_deck;
GO

CREATE PROCEDURE sp_stat_recommend_boss_battle_deck
(
	@nvc_boss_name NVARCHAR(10),
	@i_max_hero_lv INT
) AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	-- Insert statements for procedure here
	SELECT TOP 30 * FROM tbl_stat_boss_battle
	WHERE nvc_boss_name = @nvc_boss_name
	AND i_hero_lv <= @i_max_hero_lv
	ORDER BY bi_avg_damage DESC
	--OFFSET @i_offset ROWS FETCH NEXT @i_fetch ROWS ONLY

	SELECT TOP 30 * FROM tbl_stat_boss_battle
	WHERE nvc_boss_name = @nvc_boss_name
	AND i_hero_lv <= @i_max_hero_lv
	ORDER BY bi_max_damage DESC
	--OFFSET @i_offset ROWS FETCH NEXT @i_fetch ROWS ONLY
END
GO

GRANT EXECUTE ON sp_stat_recommend_boss_battle_deck TO [ss_svc]
GO