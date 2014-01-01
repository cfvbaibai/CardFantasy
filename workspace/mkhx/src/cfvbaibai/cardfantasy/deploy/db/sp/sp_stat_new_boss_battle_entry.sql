IF dbo.fn_util_sp_exists('sp_stat_new_boss_battle_entry') = 1 DROP PROCEDURE sp_stat_new_boss_battle_entry;
GO

CREATE PROCEDURE sp_stat_new_boss_battle_entry
    @nvc_boss_name NVARCHAR(10),
    @bi_battle_count BIGINT,
    @i_hero_lv INT,
    @bi_min_damage BIGINT,
    @bi_avg_damage BIGINT,
    @bi_max_damage BIGINT,
    @nvc_deck NVARCHAR(MAX)
AS
BEGIN
	DECLARE @i_max_id INT;
	DECLARE @i_new_id INT;
	
    BEGIN TRANSACTION;

    UPDATE tbl_stat_boss_battle
    SET
    	dt_created = GETUTCDATE(),
        bi_avg_damage = (bi_avg_damage * bi_battle_count + @bi_avg_damage * @bi_battle_count) / (bi_battle_count + @bi_battle_count),
        bi_min_damage = IIF(@bi_min_damage < bi_min_damage, @bi_min_damage, bi_min_damage),
        bi_max_damage = IIF(@bi_max_damage > bi_max_damage, @bi_max_damage, bi_max_damage),
        bi_battle_count = bi_battle_count + @bi_battle_count
    WHERE
            nvc_boss_name = @nvc_boss_name
        AND i_hero_lv = @i_hero_lv
        AND nvc_deck = @nvc_deck

    IF @@ROWCOUNT = 0
    BEGIN
        SET @i_max_id = (SELECT MAX(i_id) FROM tbl_stat_boss_battle);
        IF @i_max_id IS NULL BEGIN
            SET @i_new_id = 1
        END ELSE BEGIN
            SET @i_new_id = @i_max_id + 1;
        END
        
        INSERT INTO tbl_stat_boss_battle (
        	i_id,
            nvc_boss_name,
            bi_battle_count,
            i_hero_lv,
            bi_min_damage,
            bi_avg_damage,
            bi_max_damage,
            nvc_deck
        ) VALUES (
        	@i_new_id,
            @nvc_boss_name,
            @bi_battle_count,
            @i_hero_lv,
            @bi_min_damage,
            @bi_avg_damage,
            @bi_max_damage,
            @nvc_deck
        )
    END

    COMMIT TRANSACTION;
END
GO

GRANT EXECUTE ON sp_stat_new_boss_battle_entry TO [ss_svc]