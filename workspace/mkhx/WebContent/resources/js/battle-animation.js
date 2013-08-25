Kinetic.Text.prototype.center = function(rect) {
    this.setX(rect.getX() + rect.getWidth() / 2 - this.getWidth() / 2);
    return this;
};
Kinetic.Text.prototype.middle = function(rect) {
    this.setY(rect.getY() + rect.getHeight() / 2 - this.getHeight() / 2);
    return this;
};
Kinetic.Text.prototype.centerMiddle = function(rect) {
    return this.center(rect).middle(rect);
};
Array.prototype.compact = function() {
    for (var i = 0; i < this.length; i++) {
        if (!this[i]) {
            this.splice(i, 1);
            --i;
        }
    }
};

Array.prototype.removeOfName = function(name) {
    for (var i = 0; i < this.length; ++i) {
        if (this[i] && this[i].name == name) {
            return this.splice(i, 1)[0];
        }
    }
    return null;
};

Array.prototype.pickByName = function(name) {
    for (var i = 0; i < this.length; ++i) {
        if (this[i] && this[i].name == name) {
            var result = this[i];
            this[i] = null;
            return result;
        }
    }
    return null;
};

Array.prototype.indexOfName = function(name) {
    for (var i = 0; i < this.length; ++i) {
        if (this[i] && this[i].name == name) {
            return i;
        }
    }
    return -1;
};

Array.prototype.ofName = function(name) {
    for (var i = 0; i < this.length; ++i) {
        if (this[i] && this[i].name == name) {
            return this[i];
        }
    }
    return null;
};

var ArenaSettings = function() {
    this.maxWidth = 420;
    this.heightRate = 1.2;
    this.width = this.maxWidth;
    this.height = this.width * this.heightRate;
    this.minimumDuration = 0.01;

    this.bgColor = 'black';
    this.separatorColor = 'white';
    this.separatorWidth = 1;

    this.leftPanelWidthRate = 0.2;
    this.globalPadding = 5;

    this.handHeightRate = 0.2;
    
    this.fontFamily = '微软雅黑, Calibri, 新宋体, Courier New';

    this.heroIdFontSize = 12;
    this.heroIdFontFamily = this.fontFamily;
    this.heroIdColor = '#FFCCFF';
    
    this.heroHpFontSize = 10;
    this.heroHpFontFamily = this.fontFamily;
    this.heroHpColor = '#FFFFFF';

    this.hpRectStroke = '#FFFFFF';
    this.hpRectBgFill = '#222222';
    this.hpRectHpFill = '#FF0000';
    this.hpRectStrokeWidth = 4;
    
    this.runeCircleRadiusRate = 0.7;
    this.runeCircleStroke = '#999999';
    this.runeCircleStrokeWidth = 2;
    this.runeCircleFill = '#000000';
    this.runeFill = { FIRE: '#AA0000', WIND: '#22AA22', WATER: '#0000AA', GROUND: '#AA6600' };
    this.runeTextFill = { FIRE: '#FFFFFF', WIND: '#FFFFFF', WATER: '#FFFFFF', GROUND: '#FFFFFF' };
    this.runeRectHeightRate = 0.3;
    this.runeRadiusRate = 0.7;
    
    this.roundFontSize = 32;
    this.roundFontFamily = this.fontFamily;
    this.roundTextColor = 'yellow';
    this.roundTextOpacity = 0.4;
    
    this.bannerHeight = 200;
    this.bannerFill = '#222222';
    this.bannerFontSize = 18;
    this.bannerFontFamily = this.fontFamily;
    this.bannerTextColor = 'white';
    this.bannerOpacity = 0.85;
    this.bannerBorderColor = '#EEEEEE';
    this.bannerBorderWidth = 1;
    this.bannerDuration = 0.4;
    this.bannerPause = 1.3;
    
    this.handDelayFontSize = 10;
    this.handDelayColor = 'black';
    this.handDelayFontFamily = 'Aria Black, Arial';
    this.handDelayRectSize = 12;
    this.handDelayRectBorderColor = 'black';
    this.handDelayRectBorderWidth = 1;
    this.handDelayRectFill = 'white';
    this.handDelayRectOpacity = 0.65;
    
    this.portraitFrameColor = 'gold';
    this.portraitFrameWidth = 2;
    
    this.cardHpRectBorderColor = 'white';
    this.cardHpRectBorderWidth = 1;
    this.cardHpRectFill = '#222222';
    this.cardHpRectOpactiy = 0.8;
    this.cardHpRectHeight = 10;
    this.cardHpFontFamily = 'Arial';
    this.cardHpFontSize = 8;
    this.cardHpTextColor = 'white';
    this.cardHpBarOpacity = this.cardHpRectOpacity;
    this.cardHpBarFill = 'red';
    
    this.updateHpDuration = 0.5;
    
    this.cardAtRectBorderColor = this.cardHpRectBorderColor;
    this.cardAtRectBorderWidth = this.cardHpRectBorderWidth;
    this.cardAtRectFill = this.cardHpRectFill;
    this.cardAtRectOpacity = this.cardHpRectOpactiy;
    this.cardAtRectHeight = this.cardHpRectHeight;
    this.cardAtFontFamily = this.cardHpFontFamily;
    this.cardAtFontSize = this.cardHpFontSize;
    this.cardAtTextColor = this.cardHpTextColor;
    
    this.statusRectHeight = 10;
    this.statusRectBorderColor = this.cardHpRectBorderColor;
    this.statusRectBorderWidth = this.cardHpRectBorderWidth;
    this.statusRectFill = this.cardHpRectFill;
    this.statusRectOpacity = this.cardHpRectOpacity;
    this.statusFontFamily = this.cardHpFontFamily;
    this.statusFontSize = this.cardHpFontSize;
    this.statusTextColor = this.cardHpTextColor;
    
    this.drawCardDuration = 0.2;
    this.compactFieldDuration = 0.1;
    this.reincarnateDuration = 0.5;
    this.transportDuration = 0.5;
    
    this.summonCardDuration = 0.2;
    this.summonCardPause = 0.5;

    this.hpChangeDuration = 0.3;
    
    this.returnCardCrossDuration = 1;
    
    this.zoomAttackerDuration = 0.2;
    this.zoomRate = 0.2;
    
    this.normalAttackDuration = 0.3;
    this.skillDuration = 0.7;
    this.swordWidth = 14;
    this.swordHeight = 44;
    this.healWidth = 24;
    this.healHeight = 24;
    
    this.adjustDuration = 0.7;
    this.adjustFontFamily = this.fontFamily;
    this.adjustFontSize = 8;
    this.adjustAdjAtColor = '#5555FF';
    this.adjustAdjHpColor = '#FF5555';
    this.adjRectHeight = 50;
    this.adjRectBorderColor = 'white';
    this.adjRectBorderWidth = 1;
    this.adjRectFill = '#CCCCCC';
    this.adjRectOpacity = 0.95;
    
    this.cardMsgRectHeight = 50;
    this.cardMsgRectBorderColor = 'white';
    this.cardMsgRectBorderWidth = 1;
    this.cardMsgRectFill = '#CCCCCC';
    this.cardMsgRectOpacity = 0.95;
    this.cardMsgFontFamily = this.fontFamily;
    this.cardMsgFontSize = 8;
    this.cardMsgTextColor = 'black';
    this.cardMsgDuration = 0.7;
    
    this.attackCardTextColor = 'red';
    this.healCardTextColor = 'green';
    
    this.skillSplashDuration = 0.1;
    this.skillSplashPause = 0.7;

    this.refreshSize = function () {
        var currentWidth = $(window).width() * 0.8;
        if (currentWidth > this.maxWidth) { currentWidth = this.maxWidth; }
        var currentHeight = currentWidth * this.heightRate;
        this.width = currentWidth;
        this.height = currentHeight;
    };
    
    this.getLeftPanelClientWidth = function() {
        return this.getRightPanelX() - this.globalPadding * 2;
    };
    
    this.getRightPanelX = function() {
        return this.width * this.leftPanelWidthRate;
    };
    
    this.getRightPanelWidth = function() {
        return this.width - this.getRightPanelX();
    };
    
    this.getHandPanelHeight = function() {
        return this.handHeightRate * this.getPanelHeight();
    };
    
    this.getBottomPanelY = function() {
        return this.height / 2;
    };
    
    this.getPanelHeight = function() {
        return this.getBottomPanelY();
    };
    
    this.getRuneRectHeight = function() {
        return this.getPanelHeight() * this.runeRectHeightRate;
    };

    this.getRuneRadius = function() {
        var signalRadius = Math.min(this.getRuneRectHeight(), this.getLeftPanelClientWidth()) / 4;
        return signalRadius * this.runeRadiusRate;
    };
    
    this.getTopRuneRectY = function() {
        return this.getPanelHeight() - this.getRuneRectHeight() - this.globalPadding;
    };
    
    this.getBottomRuneRectY = function() {
        return this.height - this.getRuneRectHeight() - this.globalPadding; 
    };
    
    this.getLogoSize = function() {
        return { width: this.getRightPanelWidth() / 5, height: this.getPanelHeight() * this.handHeightRate };
    };
    
    this.getPortraitSize = function() {
        return {
            width: this.getRightPanelWidth() / 5,
            height: this.getPanelHeight() * (1 - this.handHeightRate) / 2,
        };
    };
    
    this.getLogoX = function(playerNumber, logoIndex) {
        return this.getRightPanelX() + logoIndex * this.getLogoSize().width;
    };
    this.getLogoY = function(playerNumber) {
        return playerNumber == 0 ? 0 : this.height - this.getHandPanelHeight();
    };
    this.getLogoPos = function(playerNumber, logoIndex) {
        return { x: this.getLogoX(playerNumber, logoIndex), y: this.getLogoY(playerNumber), };
    };
    
    this.getPortraitPos = function(playerNumber, logoIndex) {
        if (logoIndex >= 0 && logoIndex <= 4) {
            return {
                x: this.getRightPanelX() + logoIndex * this.getLogoSize().width,
                y: playerNumber == 1 ?
                        this.getPanelHeight() :
                        this.getHandPanelHeight() + this.getPortraitSize().height,
            };
        } else {
            logoIndex -= 5;
            return {
                x: this.getRightPanelX() + logoIndex * this.getLogoSize().width,
                y: playerNumber == 1 ?
                        this.getPanelHeight() + this.getPortraitSize().height :
                        this.getHandPanelHeight(),
            };
        }
    };
};
var settings = new ArenaSettings();

var Card = function(attr) {
    this.name = attr.name;
    this.group = attr.group;
    this.frame = attr.frame;
    this.hpRect = attr.hpRect;
    this.hpBar = attr.hpBar;
    this.atRect = attr.atRect;
    this.statusRect = attr.statusRect;
    this.hpText = attr.hpText;
    this.atText = attr.atText;
    this.statusText = attr.statusText;
    this.statusList = [];
};

var Arena = function(playerId, playerNumber) {
    this.playerId = playerId, playerNumber;
    this.playerNumber = playerNumber;
    this.hands = [];

    this.fields = [];
    this.runes = {};
    
    this.createLogo = function(id, name, delay, size) {
        var group = new Kinetic.Group({ x: settings.width, y: settings.height, });
        
        var delayRect = new Kinetic.Rect({
            x: size.width - settings.handDelayRectSize - 1,
            y: size.height - settings.handDelayRectSize - 1,
            width: settings.handDelayRectSize,
            height: settings.handDelayRectSize,
            stroke: settings.handDelayRectBorderColor,
            strokeWidth: settings.handDelayRectBorderWidth,
            fill: settings.handDelayRectFill,
            opacity: settings.handDelayRectOpacity,
        });
        var delayText = new Kinetic.Text({
            text: delay.toString(),
            fontFamily: settings.handDelayFontFamily,
            fontSize: settings.handDelayFontSize,
            fill: settings.handDelayColor,
        }).centerMiddle(delayRect);

        var cardAvatar = new Image();
        cardAvatar.src = resDir + '/img/cardlogo/' + id + '.jpg';
        cardAvatar.onload = function() {
            var cardAvatarImage = new Kinetic.Image({
                x : 2,
                y : 2,
                width: size.width - 4,
                height: size.height - 4,
                image : cardAvatar,
            });
            group.add(cardAvatarImage);
            var cardFrame = new Image();
            cardFrame.src = resDir + '/img/frame/frame.png';
            cardFrame.onload = function() {
                var cardFrameImage = new Kinetic.Image({
                    x : 0,
                    y : 0,
                    width: size.width,
                    height: size.height,
                    image : cardFrame,
                });
                group.add(cardFrameImage);
                group.add(delayRect);
                group.add(delayText);
            };
        };

        this.hands.push({ name: name, group: group, delay: delay, delayText: delayText });
        var layer = new Kinetic.Layer({ id: 'LL' + name });
        layer.add(group);
        return group;
    };
    
    this.createPortrait = function(card) {
        var size = settings.getPortraitSize();
        var group = new Kinetic.Group({ x: settings.width, y: settings.height, });
        var frame = new Kinetic.Rect({
            x: 0, y: 0, width: size.width, height: size.height,
            stroke: settings.portraitFrameColor,
            strokeWidth: settings.portraitFrameWidth,
            opacity: 0,
        });
        var cardAvatar = new Image();
        cardAvatar.src = resDir + '/img/cardportrait/' + card.id + '.jpg';
        var adjustY = 1;
        var hpRect = new Kinetic.Rect({
            x: 0,
            y: size.height - settings.cardHpRectHeight - adjustY,
            width: size.width,
            height: settings.cardHpRectHeight,
            stroke: settings.cardHpRectBorderColor,
            strokeWidth: settings.cardHpRectBorderWidth,
            fill: settings.cardHpRectFill,
            opacity: settings.cardHpRectOpacity,
        });
        var hpBar = new Kinetic.Rect({
            x: 0,
            y: size.height - settings.cardHpRectHeight - adjustY,
            width: size.width,
            height: settings.cardHpRectHeight,
            stroke: settings.cardHpBarFill,
            strokeWidth: 1,
            fill: settings.cardHpBarFill,
            opacity: settings.cardHpBarOpacity,
        });
        var hpBarFrame = new Kinetic.Rect({
            x: 0,
            y: size.height - settings.cardHpRectHeight - adjustY,
            width: size.width,
            height: settings.cardHpRectHeight,
            stroke: settings.cardHpRectBorderColor,
            strokeWidth: settings.cardHpRectBorderWidth,
            fill: 'transparent',
            opacity: settings.cardHpRectOpacity,
        });
        var atRect = new Kinetic.Rect({
            x: 0,
            y: size.height - settings.cardAtRectHeight - settings.cardHpRectHeight - adjustY,
            width: size.width,
            height: settings.cardAtRectHeight,
            stroke: settings.cardAtRectBorderColor,
            strokeWidth: settings.cardAtRectBorderWidth,
            fill: settings.cardAtRectFill,
            opacity: settings.cardAtRectOpacity,
        });
        var hpText = new Kinetic.Text({
            text: 'HP: ' + card.hp,
            fontFamily: settings.cardHpFontFamily,
            fontSize: settings.cardHpFontSize,
            fill: settings.cardHpTextColor,
        }).centerMiddle(hpRect);
        var atText = new Kinetic.Text({
            text: 'AT: ' + card.at,
            fontFamily: settings.cardAtFontFamily,
            fontSize: settings.cardAtFontSize,
            fill: settings.cardAtTextColor,
        }).centerMiddle(atRect);
        
        var statusRect = new Kinetic.Rect({
            x: 0, y: 0, width: size.width, height: settings.statusRectHeight + adjustY,
            stroke: settings.statusRectBorderColor,
            strokeWidth: settings.statusRectBorderWidth,
            fill: settings.statusRectFill,
            opacity: settings.statusRectOpacity,
        });
        var statusText = new Kinetic.Text({
            text: '',
            fontFamily: settings.statusFontFamily,
            fontSize: settings.statusFontSize,
            fill: settings.statusTextColor,
        }).centerMiddle(statusRect);
        cardAvatar.onload = function() {
            var cardAvatarImage = new Kinetic.Image({
                x : 2,
                y : 2,
                width: size.width - 4,
                height: size.height - 4,
                image : cardAvatar,
            });
            group.add(cardAvatarImage).add(hpRect).add(hpBar).add(hpBarFrame).add(atRect);
            group.add(hpText).add(atText).add(statusRect).add(statusText).add(frame);
        };
        this.fields.push(new Card({
            name: card.name, group: group, frame: frame,
            hpRect: hpRect, hpBar: hpBar, atRect: atRect, statusRect: statusRect,
            hpText: hpText, atText: atText, statusText: statusText,
        }));
        var layer = new Kinetic.Layer({ id: 'LP' + card.name });
        layer.add(group);
        return group;
    };
};

Arena.createCross = function() {
    var size = settings.getPortraitSize();
    var group = new Kinetic.Group({ x: settings.width, y: settings.height, });
    var paddingX = size.width * 0.2;
    var paddingY = size.height * 0.2;
    var verX = size.width * 0.5;
    var hozY = (size.height - paddingY) * 0.3 + paddingY;
    var crossLine = new Kinetic.Line({
        points: [
                 paddingX, hozY,
                 size.width - paddingX, hozY,
                 verX, hozY,
                 verX, paddingY,
                 verX, size.height
                 ],
        stroke: 'white',
        strokeWidth: 5,
        shadowColor: '#333333',
        shadowBlur: 10,
        shadowOffset: 0,
        shadowOpacity: 0.4,
    });
    group.add(crossLine);
    return group;
};

Arena.createSword = function() {
    var group = new Kinetic.Group({
        x: settings.width,
        y: settings.height,
    });

    var swordSrc = new Image();
    swordSrc.src = resDir + '/img/effect/sword.png';
    swordSrc.onload = function() {
        var swordImg = new Kinetic.Image({
            x : 0,
            y : 0,
            image : swordSrc,
        });
        group.add(swordImg);
    };
    return group;
};

var Animater = function() {
    this.initTime = 0.5;
    this.time = 0;
    this.stage = null;
    this.arenas = {};

    this.drawRunes = function(layer, positions) {
        for (var i = 0; i < positions.length; ++i) {
            var pos = positions[i];
            layer.add(new Kinetic.Circle({
                id: pos.id + '-circle',
                x: pos.x,
                y: pos.y,
                radius: settings.getRuneRadius(),
                stroke: settings.runeCircleStroke,
                strokeWidth: settings.runeCircleStrokeWidth,
                fill: settings.runeCircleFill,
            }));
            layer.add(new Kinetic.Text({
                id: pos.id + '-name',
                fontFamily: settings.fontFamily,
                fontSize: 8,
                fill: 'white',
            }));
            layer.add(new Kinetic.Text({
                id: pos.id + '-energy',
                fontFamily: settings.fontFamily,
                fontSize: 8,
                fill: 'white',
            }));
        }
    };
    
    this.__getPrefix = function(player) {
        var playerNumber = null;
        if ($.type(player) == 'string') {
            playerNumber = this.arenas[player].playerNumber;
        } else {
            playerNumber = player.number;
        }
        return playerNumber == 0 ? 'hero1-' : 'hero2-';
    };
    
    this.__getShape = function(player, idSuffix) {
        var prefix = this.__getPrefix(player);
        return this.stage.get('#' + prefix + idSuffix)[0];
    };

    this.__updateHeroHp = function(player) {
        var hp = player.hp;
        var maxHp = player.maxHP;
        var hpTextShape = this.__getShape(player, 'hp');
        var hpRectShape = this.__getShape(player, 'hpbg-rect');
        var hpBarShape = this.__getShape(player, 'hp-bar');
        var hpText = hp > 99999 ? '?????' : hp.toString();
        var maxHpText = maxHp > 99999 ? '?????' : maxHp.toString();
        this.addAnimation("updateHeroHP", function() {
            hpTextShape.setText('HP:\r\n' + hpText + '/\r\n' + maxHpText);
            hpTextShape.middle(hpRectShape).center(hpRectShape);
            new Kinetic.Tween({
                node: hpBarShape,
                height: hp * hpRectShape.getHeight() / maxHp,
                y: hpRectShape.getY() + hpRectShape.getHeight() - hp * hpRectShape.getHeight() / maxHp,
                duration: settings.hpChangeDuration,
            }).play();
        }, settings.hpChangeDuration);
    };
    
    this.__updateRune = function(player, runeInfo) {
        var self = this;
        this.addAnimation('updateRune', function () {
            var arena = self.arenas[player.id];
            var i = arena.runes[runeInfo.name];
            var runeCircleShape = self.__getShape(player, 'rune' + i + '-circle');
            runeCircleShape.setFill(settings.runeFill[runeInfo.type]);
            var runeNameShape = self.__getShape(player, 'rune' + i + '-name');
            runeNameShape.setText(runeInfo.name);
            runeNameShape.setFill(settings.runeTextFill[runeInfo.type]);
            runeNameShape.setX(runeCircleShape.getX() - runeNameShape.getWidth() / 2);
            runeNameShape.setY(runeCircleShape.getY() - runeNameShape.getHeight() + 2);
            var runeEnergyShape = self.__getShape(player, 'rune' + i + '-energy');
            runeEnergyShape.setText(runeInfo.energy);
            runeEnergyShape.setFill(settings.runeTextFill[runeInfo.type]);
            runeEnergyShape.setX(runeCircleShape.getX() - runeEnergyShape.getWidth() / 2);
            runeEnergyShape.setY(runeCircleShape.getY() + 2);
            runeCircleShape.getLayer().draw();
        }, settings.minimumDuration);
    };
    
    this.__updateRunes = function(player, runeInfos) {
        for (var i = 0; i < runeInfos.length; ++i) {
            var runeInfo = runeInfos[i];
            var arena = this.arenas[player.id];
            arena.runes[runeInfo.name] = i;
            this.__updateRune(player, runeInfo, i);
        }
    };
    
    this.__stageCreated = function(data) {
        var frameLayer = new Kinetic.Layer({ id: 'frame-layer' });
        // Draw vertical separator line
        frameLayer.add(new Kinetic.Line({
            points: [
                settings.getRightPanelX(), 0,
                settings.getRightPanelX(), settings.height,
            ],
            stroke: settings.separatorColor,
            strokeWidth: settings.separatorWidth,
        }));
        // Draw horizontal separator line
        frameLayer.add(new Kinetic.Line({
            points: [
                0, settings.getBottomPanelY(),
                settings.width, settings.getBottomPanelY(),
            ],
            stroke: settings.separatorColor,
            strokeWidth: settings.separatorWidth * 2,
        }));
        
        // Draw hero label for player1
        var hero1Id = new Kinetic.Text({
            id: 'hero1-id',
            x: settings.globalPadding,
            y: settings.globalPadding,
            text: '玩家1',
            fontSize: settings.heroIdFontSize,
            fontFamily: settings.heroIdFontFamily,
            fill: settings.heroIdColor,
        });
        frameLayer.add(hero1Id);
        
        // Draw hero HP rectangle for player1
        var hp1RectY = hero1Id.getHeight() + hero1Id.getY() + settings.globalPadding;
        var hp1Rect = new Kinetic.Rect({
            id: 'hero1-hpbg-rect',
            x: settings.globalPadding,
            y: hp1RectY,
            width: settings.getLeftPanelClientWidth(),
            height: settings.getTopRuneRectY() - settings.globalPadding - hp1RectY,
            stroke: settings.hpRectStroke,
            strokeWidth: settings.hpRectStrokeWidth,
            fill: settings.hpRectBgFill,
        });
        frameLayer.add(hp1Rect);

        // Draw hero label for player2
        var hero2Id = new Kinetic.Text({
            id: 'hero2-id',
            x: settings.globalPadding,
            y: settings.globalPadding + settings.getBottomPanelY(),
            text: '玩家2',
            fontSize: settings.heroIdFontSize,
            fontFamily: settings.heroIdFontFamily,
            fill: settings.heroIdColor,
        });
        frameLayer.add(hero2Id);
        
        // Draw hero HP rectangle for player2
        var hp2RectY = hero2Id.getHeight() + hero2Id.getY() + settings.globalPadding;
        var hp2Rect = new Kinetic.Rect({
            id: 'hero2-hpbg-rect',
            x: settings.globalPadding,
            y: hp2RectY,
            width: settings.getLeftPanelClientWidth(),
            height: settings.getBottomRuneRectY() - settings.globalPadding - hp2RectY,
            stroke: settings.hpRectStroke,
            strokeWidth: settings.hpRectStrokeWidth,
            fill: settings.hpRectBgFill,
        });
        frameLayer.add(hp2Rect);
        
        // Draw rune circle for player1
        var rune1Circle = new Kinetic.Ellipse({
            x: settings.getRightPanelX() / 2,
            y: settings.getTopRuneRectY() + settings.getRuneRectHeight() / 2,
            radius: {
                x: settings.getLeftPanelClientWidth() / 2 * settings.runeCircleRadiusRate,
                y: settings.getRuneRectHeight() / 2 * settings.runeCircleRadiusRate,
            },
            stroke: settings.runeCircleStroke,
            strokeWidth: settings.runeCircleStrokeWidth,
            fill: settings.runeCircleFill,
        });
        frameLayer.add(rune1Circle);

        // Draw rune circle for player2
        var rune2Circle = new Kinetic.Ellipse({
            x: settings.getRightPanelX() / 2,
            y: settings.getBottomRuneRectY() + settings.getRuneRectHeight() / 2,
            radius: {
                x: settings.getLeftPanelClientWidth() / 2 * settings.runeCircleRadiusRate,
                y: settings.getRuneRectHeight() / 2 * settings.runeCircleRadiusRate,
            },
            stroke: settings.runeCircleStroke,
            strokeWidth: settings.runeCircleStrokeWidth,
            fill: settings.runeCircleFill,
        });
        frameLayer.add(rune2Circle);

        // Draw hand/field separators
        var hfSep1Y = settings.height / 2 * settings.handHeightRate; 
        frameLayer.add(new Kinetic.Line({
            points: [
                settings.getRightPanelX(), hfSep1Y,
                settings.width, hfSep1Y,
            ],
            stroke: settings.separatorColor,
            strokeWidth: settings.separatorWidth,
        }));
        
        var hfSep2Y = settings.height - hfSep1Y;
        frameLayer.add(new Kinetic.Line({
            points: [
                settings.getRightPanelX(), hfSep2Y,
                settings.width, hfSep2Y,
            ],
            stroke: settings.separatorColor,
            strokeWidth: settings.separatorWidth,
        }));
        
        // Draw horizontal inner separators
        var inSep1Y = hfSep1Y + (settings.height / 2 - hfSep1Y) / 2;
        frameLayer.add(new Kinetic.Line({
            points: [
                 settings.getRightPanelX(), inSep1Y,
                 settings.width, inSep1Y,
             ],
             stroke: settings.separatorColor,
             strokeWidth: settings.separatorWidth,
        }));
        
        var inSep2Y = settings.height - inSep1Y;
        frameLayer.add(new Kinetic.Line({
            points: [
                 settings.getRightPanelX(), inSep2Y,
                 settings.width, inSep2Y,
             ],
             stroke: settings.separatorColor,
             strokeWidth: settings.separatorWidth,
        }));
        
        // Draw vertical inner separators
        var rightPanelWidth = settings.width - settings.getRightPanelX();
        for (var i = 1; i <= 4; ++i) {
            var inSepX = settings.getRightPanelX()  + i * rightPanelWidth / 5;
            frameLayer.add(new Kinetic.Line({
                points: [ inSepX, 0, inSepX, settings.height ],
                stroke: settings.separatorColor,
                strokeWidth: settings.separatorWidth, 
            }));
        }

        // Append frame layer
        this.stage.add(frameLayer);
        // Append HP shapes to card layer
        var cardLayer = new Kinetic.Layer({ id: 'card-layer' });
        // Draw rune outlines for both players
        this.drawRunes(cardLayer, [
            { id: 'hero1-rune0', x: rune1Circle.getX() - rune1Circle.getRadius().x, y: rune1Circle.getY(), },
            { id: 'hero1-rune1', x: rune1Circle.getX(), y: rune1Circle.getY() - rune1Circle.getRadius().y, },
            { id: 'hero1-rune2', x: rune1Circle.getX() + rune1Circle.getRadius().x, y: rune1Circle.getY(), },
            { id: 'hero1-rune3', x: rune1Circle.getX(), y: rune1Circle.getY() + rune1Circle.getRadius().y, },
            { id: 'hero2-rune0', x: rune2Circle.getX() - rune2Circle.getRadius().x, y: rune2Circle.getY(), },
            { id: 'hero2-rune1', x: rune2Circle.getX(), y: rune2Circle.getY() - rune2Circle.getRadius().y, },
            { id: 'hero2-rune2', x: rune2Circle.getX() + rune2Circle.getRadius().x, y: rune2Circle.getY(), },
            { id: 'hero2-rune3', x: rune2Circle.getX(), y: rune2Circle.getY() + rune2Circle.getRadius().y, },
        ]);
        cardLayer.add(new Kinetic.Rect({
            id: 'hero1-hp-bar',
            x: hp1Rect.getX(),
            y: hp1Rect.getY() + hp1Rect.getHeight(),
            width: hp1Rect.getWidth(),
            height: 0,
            fill: settings.hpRectHpFill,
        }));
        cardLayer.add(new Kinetic.Rect({
            id: 'hero2-hp-bar',
            x: hp2Rect.getX(),
            y: hp2Rect.getY() + hp2Rect.getHeight(),
            width: hp2Rect.getWidth(),
            height: 0,
            fill: settings.hpRectHpFill,
        }));
        cardLayer.add(new Kinetic.Text({
            id: 'hero1-hp',
            text: '******\r\n******\r\n******',
            fontSize: settings.heroHpFontSize,
            fontFamily: settings.heroHpFontFamily,
            fill: settings.heroHpColor,
        }).center(hp1Rect).middle(hp1Rect));

        cardLayer.add(new Kinetic.Text({
            id: 'hero2-hp',
            text: '******\r\n******\r\n******',
            fontSize: settings.heroHpFontSize,
            fontFamily: settings.heroHpFontFamily,
            fill: settings.heroHpColor,
        }).center(hp2Rect).middle(hp2Rect));
        this.stage.add(cardLayer);
        // Append other layers
        this.stage.add(new Kinetic.Layer({ id: 'effect-layer' }));
        
        var roundLayer = new Kinetic.Layer({ id: 'round-layer' });
        var roundRect = new Kinetic.Rect({
            id: 'round-rect', x: 0, y: 0, width: settings.width, height: settings.height, opacity: 0,
        });
        roundLayer.add(roundRect);
        var roundText = new Kinetic.Text({
            id: 'round-text',
            text: '回合: 0',
            fontSize: settings.roundFontSize,
            fontFamily: settings.roundFontFamily,
            fill: settings.roundTextColor,//'yellow',
            opacity: settings.roundTextOpacity,//0.4,
        });
        roundLayer.add(roundText);
        this.stage.add(roundLayer);
        roundText.centerMiddle(roundRect);
        roundLayer.draw();
        
        var splashLayer = new Kinetic.Layer({ id: 'splash-layer' });
        var splashRect = new Kinetic.Rect({
            id: 'splash-label',
            x: -settings.width,
            y: settings.height / 2 - 100,
            width: settings.width,
            height: settings.bannerHeight,
            strokeWidth: settings.bannerBorderWidth,
            stroke: settings.bannerBorderColor,
            fill: settings.bannerFill,
            opacity: settings.bannerOpacity,
        });
        var splashText = new Kinetic.Text({
            id: 'splash-text',
            text: '',
            fontSize: settings.bannerFontSize,
            fontFamily: settings.bannerFontFamily,
            fill: settings.bannerTextColor,
        });
        splashLayer.add(splashRect).add(splashText);
        this.stage.add(splashLayer);
    };
    
    this.__playerAdded = function(data) {
        var player = data[0];
        var prefix = this.__getPrefix(player);
        var heroId = this.stage.get('#' + prefix + 'id')[0];
        heroId.setText(player.id);
        heroId.getLayer().draw();
        var arena = new Arena(player.id, player.number);
        this.arenas[player.id] = arena;

        this.__updateHeroHp(player, player.maxHP, player.maxHP);
        this.__updateRunes(player, player.runeInitInfos);
    };

    this.__gameStarted = function(data) {
        this.showSplash({ text: '战斗开始!' });
    };
    
    this.__roundStarted = function(data) {

    };
    
    this.__compactField = function(data) {
        var arena = this.arenas[data[0]];
        var fields = arena.fields;
        fields.compact();
        var funcs = [];
        for (var i = 0; i < fields.length; ++i) {
            (function(iCard, card) {
                var pos = settings.getPortraitPos(arena.playerNumber, iCard);
                funcs.push(function() {
                    new Kinetic.Tween({
                        node: card.group,
                        x: pos.x,
                        y: pos.y,
                        duration: settings.compactFieldDuration,
                    }).play();
                });
            })(i, fields[i]);
        }
        this.addAnimations("compactField", funcs, settings.compactFieldDuration);
    };
    
    this.__roundEnded = function(data) {
        var round = data[0];
        var playerId = data[1];
        var stage = this.stage;
        var roundText = stage.get('#round-text')[0];
        
        // Remove all debuffs.
        var self = this;
        $.each(this.arenas[playerId].fields, function (i, card) {
            var newStatusList = [];
            for (var i = 0; i < card.statusList.length; ++i) {
                if (card.statusList[i] == '燃') {
                    newStatusList.push('燃');
                } else if (card.statusList[i] == '裂') {
                    newStatusList.push('裂');
                }
            }
            self.addAnimation("clearStatus", function() {
                card.statusText.setText(newStatusList.join());
                card.statusText.centerMiddle(card.statusRect);
                card.group.getLayer().draw();
            }, settings.minimumDuration);
        });
        
        this.addAnimation("roundEnded", function() {
            roundText.setText("回合: " + round);
            roundText.centerMiddle(stage.get('#round-rect')[0]);
            roundText.getLayer().draw();
        }, settings.minimumDuration);
        var funcs = [];
        for (var arena in this.arenas) {
            var hands = this.arenas[arena].hands;
            for (var i = 0; i < hands.length; ++i) {
                (function (logo) {
                    funcs.push(function () {
                        if (logo.delay <= 0) { return; }
                        logo.delay -= 1;
                        logo.delayText.setText(logo.delay.toString());
                        logo.delayText.getLayer().draw();
                    });
                })(hands[i]);
            }
        }
        this.addAnimations("delayDecrease", funcs, 0.3);
    };
    
    this.__activateRune = function(data) {
        var player = data[0];
        var rune = data[1];
        var shouldNotice = data[2];
        if (shouldNotice) {
            this.showSplash({
                text: player.id + " 的 " + rune.name + " 激活！",
            });
        }
        this.__updateRune(player, rune);
    };
    
    this.__deactivateRune = function(data) {
        var player = data[0];
        var rune = data[1];
        var shouldNotice = data[2];
        if (shouldNotice) {
            this.showSplash({
                text: player.id + " 的 " + rune.name + " 能量耗尽！",
            });
        }
    };
    
    this.__cardDrawed = function(data) {
        var playerId = data[0];
        var arena = this.arenas[playerId];
        var targetIndex = arena.hands.length;
        var pn = arena.playerNumber;
        var logo = arena.createLogo(data[1], data[2], data[3], settings.getLogoSize());
        this.addSingleLayerShape(logo);
        this.addAnimation("drawCard", function() {
            logo.setX(settings.width);
            logo.setY(settings.getLogoY(pn));
            new Kinetic.Tween({
                node: logo,
                x: settings.getLogoX(pn, targetIndex),
                duration: settings.drawCardDuration
            }).play();
        }, settings.drawCardDuration);
    };

    this.__summonCard = function(data) {
        var playerId = data[0];
        var card = data[1];
        var arena = this.arenas[playerId];
        
        var logo = arena.hands.removeOfName(card.name);
        var handFuncs = [];
        handFuncs.push(function() {
            new Kinetic.Tween({
                node: logo.group,
                x: -settings.getLogoSize().width,
                duration: settings.drawCardDuration,
            }).play();
        });
        this.compactHands(arena, handFuncs);
        this.destroySingleLayerShape(logo);

        var targetIndex = arena.fields.length;
        var portrait = arena.createPortrait(card, settings.getPortraitSize());
        this.addSingleLayerShape(portrait);
        this.addAnimation("summonCard", function() {
            var pos = settings.getPortraitPos(arena.playerNumber, targetIndex);
            new Kinetic.Tween({
                node: portrait,
                x: pos.x,
                y: pos.y,
                duration: settings.summonCardDuration,
            }).play();
        }, settings.summonCardDuration);
        this.pauseAnimation(settings.summonCardPause);
    };
    
    this.__cardActionBegins = function(data) {
        var cardRtInfo = data[0];
        var card = this.getCard(cardRtInfo);
        if (card) {
            this.addAnimation("cardActionBegins", function() {
                card.frame.setOpacity(1);
                card.frame.getLayer().draw();
            }, settings.minimumDuration);
        }
    };
    
    this.__cardActionEnds = function(data) {
        var cardRtInfo = data[0];
        var card = this.getCard(cardRtInfo, true);
        if (card) {
            this.addAnimation("cardActionEnds", function() {
                card.frame.setOpacity(0);
                card.frame.getLayer().draw();
            }, settings.minimumDuration);
        }
    };

    this.__adjustAT = function(data) {
        this.adjustValue(data, 'AT', true);
    };
    
    this.__lostAdjAT = function(data) {
        this.adjustValue(data, 'AT', false);
    };
    
    this.__adjustHP = function(data) {
        this.adjustValue(data, 'HP', true);
    };
    
    this.__lostAdjHP = function(data) {
        this.adjustValue(data, 'HP', false);
    };
    
    this.__addCardStatus = function(data) {
        //var attacker = data[0];
        var defender = data[1];
        var featureName = data[2];
        var longStatus = data[3];
        var shortStatus = data[4];
        var defenderCard = this.getCard(defender, true);
        if (defenderCard == null) {
            // 裂伤再死亡之后发动就有可能造成null
            return;
        }
        this.displayCardMsg({
            name: 'addCardStatus',
            cardShape: defenderCard.group,
            text: featureName + '\r\n\r\n导致\r\n\r\n' + longStatus, 
        });

        if (shortStatus != '裂' || defenderCard.statusList.indexOf('裂') < 0) {
            defenderCard.statusList.push(shortStatus);
        }
        var text = defenderCard.statusList.join();
        this.addAnimation('updateCardStatus', function() {
            defenderCard.statusText.setText(text);
            defenderCard.statusText.centerMiddle(defenderCard.statusRect);
            defenderCard.group.getLayer().draw();
        }, settings.minimumDuration);
    };
    
    this.__debuffDamage = function(data) {
        var cardRtInfo = data[0];
        var status = data[1];
        var damage = data[2];
        var currentHP = data[3];
        var maxHP = data[4];
        var card = this.getCard(cardRtInfo);
        this.displayCardMsg({
            name: 'debuffDamage',
            cardShape: card.group,
            text: status + '\r\n\r\n造成伤害\r\n\r\n' + damage,
            textColor: 'red',
        });
        this.updateCardHP(card, currentHP, maxHP);
    };
    
    this.__attackHero = function(data) {
        var attacker = data[0];
        var defenderHero = data[1];
        var damage = data[2];
        var featureName = data[3];
        this.normalAttack(attacker, defenderHero, true);
        this.displayCardMsg({
            name: 'attackHeroMsg',
            cardShape: this.__getShape(defenderHero, 'hpbg-rect'),
            textColor: settings.attackCardTextColor,
            text: '伤害: ' + damage + '\r\n' + featureName,
        });
        defenderHero.hp -= damage;
        if (defenderHero.hp < 0) {
            defenderHero.hp = 0;
        }
        this.__updateHeroHp(defenderHero);
    };
    
    this.__attackCard = function(data) {
        //var attacker = data[0];
        var defender = data[1];
        var featureName = data[2];
        var damage = data[3];
        var currentHP = data[4];
        var maxHP = data[5];
        var dfCard = this.getCard(defender);
        this.displayCardMsg({
            name: 'attackCard',
            cardShape: dfCard.group,
            textColor: settings.attackCardTextColor,
            text: '伤害: ' + damage + '\r\n' + featureName,
        });
        this.updateCardHP(dfCard, currentHP, maxHP);
    };
    
    this.__healHero = function(data) {
        //var healer = data[0];
        var healee = data[1];
        var featureName = data[2];
        var heal = data[3];
        var currentHP = data[4];
        this.displayCardMsg({
            name: 'healHeroMsg',
            cardShape: this.__getShape(healee, 'hpbg-rect'),
            textColor: settings.healCardTextColor,
            text: '治疗: ' + heal + '\r\n' + featureName,
        });
        healee.hp = currentHP;
        this.__updateHeroHp(healee);
    };

    this.__healCard = function(data) {
        //var healer = data[0];       // EntityRuntimeInfo
        var healee = data[1];       // EntityRuntimeInfo
        var featureName = data[2];  // String
        var heal = data[3];         // int
        var currentHP = data[4];    // int
        var maxHP = data[5];
        var healeeCard = this.getCard(healee);
        this.displayCardMsg({
            name: 'healCard',
            cardShape: healeeCard.group,
            textColor: settings.healCardTextColor,
            text: '治疗: ' + heal + '\r\n' + featureName,
        });
        this.updateCardHP(healeeCard, currentHP, maxHP);
        /*
        this.addAnimation('healCardUpdateHp', function () {
            healeeCard.hpText.setText('HP: ' + currentHP);
            healeeCard.hpText.centerMiddle(healeeCard.hpRect);
            healeeCard.hpText.getLayer().draw();
        }, settings.minimumDuration);
        */
    };
    
    this.__healBlocked = function(data) {
        //var healer = data[0];
        var healee = data[1];
        var healFeature = data[2];
        //var blockFeature = data[3];
        var healeeCard = this.getCard(healee);
        this.displayCardMsg({
            name: 'healBlocked',
            cardShape: healeeCard.group,
            text: healFeature + '\r\n无效',
        });
    };

    this.__blockDamage = function(data) {
        var protector = data[0];
        //var attacker = data[1];
        var defender = data[2];
        var featureName = data[3];
        var originalDamage = data[4];
        var actualDamage = data[5];
        /*
        if (protector.uniqueName != defender.uniqueName) {
            console.error("Protector(" + protector.uniqueName + ") != Defender(" +
                    defender.uniqueName + "). Not supported yet!");
            return;
        }
        */
        var dfCard = this.getCard(defender);
        this.displayCardMsg({
            name: 'blockDamage',
            cardShape: dfCard.group,
            textColor: settings.blockDamageTextColor,
            text: protector.uniqueName + '\r\n' + featureName + '\r\n伤害: ' + originalDamage + '\r\n-->' + actualDamage,
        });
    };

    this.msgIgnoredSkills = ['背刺', '暴击', '狂热', '嗜血', '横扫', '冰甲', '闪避', '盾刺', '反击'];
    this.__useSkill = function(data) {
        var attacker = data[0]; // EntityRuntimeInfo
        var skill = data[1];    // String
        var defenders = data[2]; // EntityRuntimeInfo[]
        if (defenders.length == 0) {
            return;
        }
        if (skill.indexOf('军团') == 0 ||
            skill.indexOf('守护') > 0 ||
            skill.indexOf('之力') > 0 ||
            this.msgIgnoredSkills.indexOf(skill) >= 0) {
            return;
        }
        if (skill == '普通攻击') {
            this.normalAttack(attacker, defenders[0], false);
        } else if (skill == '送还') {
            this.showReturnCross(attacker, defenders[0]);
        } else if (skill == '治疗' || skill == '甘霖') {
            this.flyImage({ fileName: 'heal.png', width: settings.healWidth, height: settings.healHeight },
                    attacker, defenders, settings.skillDuration);
        } else {
            var text = attacker.ownerId + "的" + attacker.uniqueName + "\r\n";
            text += "\r\n" + skill + "\r\n\r\n";
            for (var i = 0; i < defenders.length; ++i) {
                text += defenders[i].uniqueName + '\r\n';
            }
            this.showSplash({
                text: text,
                duration: settings.skillSplashDuration,
                pause: settings.skillSplashPause,
            });
        }
    };
    
    this.__useSkillToHero = function(data) {
        var attacker = data[0]; // EntityRuntimeInfo
        var skill = data[1];    // String
        var defenderHero = data[2]; // PlayerRuntimeInfo
        if (skill.indexOf('军团') == 0 ||
            skill.indexOf('守护') > 0 ||
            skill.indexOf('之力') > 0 ||
            this.msgIgnoredSkills.indexOf(skill) >= 0) {
            return;
        }
        if (skill == '普通攻击') {
            // Already animated in __attackHero
        } else {
            var text = attacker.ownerId + "的" + attacker.uniqueName + "\r\n";
            text += "\r\n" + skill + "\r\n\r\n" + defenderHero.id;
            this.showSplash({
                text: text,
                duration: settings.skillSplashDuration,
                pause: settings.skillSplashPause,
            });
        }
    };
    
    this.showReturnCross = function(attacker, defender) {
        var attackerArena = this.arenas[attacker.ownerId];
        var defenderArena = this.arenas[defender.ownerId];
        var defenderCardIndex = defenderArena.fields.indexOfName(defender.uniqueName);
        var cross = null;
        var self = this;
        this.addAnimation("showCross", function() {
            cross = Arena.createCross();
            self.stage.get('#effect-layer')[0].add(cross);
            var crossSartPos = settings.getPortraitPos(attackerArena.playerNumber, defenderCardIndex);
            var crossEndPos = settings.getPortraitPos(defenderArena.playerNumber, defenderCardIndex);
            cross.setX(crossSartPos.x);
            cross.setY(crossSartPos.y);
            new Kinetic.Tween({
                node: cross,
                x: crossEndPos.x,
                y: crossEndPos.y,
                easing: Kinetic.Easings.StrongEaseIn,
                duration: settings.returnCardCrossDuration,
            }).play();
        }, settings.returnCardCrossDuration);
        this.addAnimation("destroyCross", function() {
            cross.destroy();
            delete cross;
        }, settings.minimumDuration);
    };
    
    this.__returnCard = function(data) {
        // var attacker = data[0];
        var defender = data[1];
        var defenderArena = this.arenas[defender.ownerId];
        var card = defenderArena.fields.pickByName(defender.uniqueName);
        this.addAnimation("returnCard", function() {
            new Kinetic.Tween({
                node: card.group,
                x: settings.width,
                duration: settings.summonCardDuration,
            }).play();
        }, settings.summonCardDuration);
        this.destroySingleLayerShape(card);
    };
    
    this.__cardDead = function(data) {
        var playerId = data[0];
        var arena = this.arenas[playerId];
        var card = arena.fields.pickByName(data[1].name);
        this.displayCardMsg({
            name: 'cardDeadMsg',
            cardShape: card.group,
            textColor: 'red',
            text: '死亡',
        });
        this.addAnimation("removeDeadCard", function() {
            card.hpText.setText('DEAD');
            card.hpText.setFill('red');
            card.hpText.centerMiddle(card.hpRect);
            new Kinetic.Tween({
                node: card.group,
                x: settings.width,
                duration: settings.summonCardDuration,
            }).play();
        }, settings.summonCardDuration);
        this.destroySingleLayerShape(card);
    };

    this.__cardToHand = function(data) {
        var player = data[0];
        var cardRtInfo = data[1];
        var cardId = data[2];
        var delay = data[3];
        var arena = this.arenas[player.id];
        var targetIndex = arena.hands.length;
        var logoSize = settings.getLogoSize();
        var logo = arena.createLogo(cardId, cardRtInfo.uniqueName, delay, logoSize);
        this.addSingleLayerShape(logo);
        this.addAnimation("cardToHand", function() {
            logo.setX(settings.getLogoX(player.number, targetIndex));
            logo.setY(player.number == 0 ? -logoSize.height : settings.height);
            new Kinetic.Tween({
                node: logo,
                y: settings.getLogoY(player.number),
                duration: settings.reincarnateDuration,
            }).play();
        }, settings.reincarnateDuration);
    };
    
    this.__cardToGrave = function(data) {
        var playerId = data[0];
        var cardRtInfo = data[1];
        var arena = this.arenas[playerId];
        var hands = arena.hands;
        var logo = hands.removeOfName(cardRtInfo.uniqueName);
        this.addAnimation("cardToGrave", function() {
            new Kinetic.Tween({
                node: logo.group,
                x: settings.width,
                duration: settings.transportDuration,
            }).play();
        }, settings.transportDuration);
        this.compactHands(arena);
    };
    
    this.__gameEnded = function(data) {
        var player = data[0];
        // var loser = data[1];
        // var cause = data[2];
        this.showSplash({ text: '战斗结束!\r\n获胜者: ' + player.id + (data[3] > 0 ? ('\r\n共造成伤害: ' + data[3]) : ''), });
        this.showSplash({ text: '这个功能还没完成，\r\n就做了那么点儿，\r\n白白会努力做的！^0^', exitType: 'onclick', });
    };
    
    /**
     * @param
     * card.ownerId,
     * card.uniqueName,
     * @return { x, y }
     */
    this.getCard = function(cardRtInfo, suppressErrorLog) {
        var card = this.arenas[cardRtInfo.ownerId].fields.ofName(cardRtInfo.uniqueName);
        if (card == null && !suppressErrorLog) {
            console.error('ERROR: Cannot find card ' + cardRtInfo.uniqueName + ' from ' + cardRtInfo.ownerId);
        } 
        return card;
    };
    
    this.addSingleLayerShape = function(shape) {
        this.stage.add(shape.getLayer());
        shape.getLayer().setZIndex(this.stage.get('#card-layer')[0].getZIndex() + 1);
    };

    this.destroySingleLayerShape = function(shape) {
        this.addAnimation("destroySingleLayerShape", function() {
            shape.group.getLayer().destroy();
            shape.group.destroy();
            delete shape;
        }, settings.minimumDuration);
    };

    this.compactHands = function(arena, funcs) {
        if (!funcs) {
            funcs = [];
        }
        for (var iHand = 0; iHand < arena.hands.length; ++iHand) {
            (function(i, currentCard) {
                var card = currentCard;
                //if (card == logo) { return; }
                var pos = settings.getLogoPos(arena.playerNumber, i);
                funcs.push(function () {
                    new Kinetic.Tween({
                        node: card.group,
                        x: pos.x,
                        y: pos.y,
                        duration: settings.drawCardDuration,
                    }).play();
                });
            })(iHand, arena.hands[iHand]);
        }
        this.addAnimations("compactHands", funcs, settings.drawCardDuration);
    };
    
    this.getRune = function(runeRtInfo) {
        var arena = this.arenas[runeRtInfo.ownerId];
        var index = arena.runes[runeRtInfo.uniqueName];
        var suffix = 'rune' + index + '-circle';
        return this.__getShape(runeRtInfo.ownerId, suffix);
    };
    
    /**
     * @param img Object { fileName, width, height }
     * @param source The source entity (EntityRuntimeInfo) from which image flies
     * @param targets The target entities (EntityRuntimeInfo) or hero (PlayerRuntimeInfo) to which image flies
     */ 
    this.flyImage = function(imgObj, source, targetEntities, duration) {
        var self = this;
        if (!duration) { duration = settings.normalAttackDuration; }
        if (source.type != 'Card' && source.type != 'Rune') {
            console.error("Invalid source type: " + source.type);
            return;
        }
        var isCardSource = source.type == 'Card';
        var sourceShape = isCardSource ?
            this.getCard(source).group :
            this.getRune(source);
        var isHeroTarget = $.type(targetEntities) != 'array';
        var targets = [];
        if (isHeroTarget) {
            targets.push(self.__getShape(targetEntities, 'hpbg-rect'));
        } else {
            $.each(targetEntities, function(i, c) {
                targets.push(self.getCard(c));
            });
        }
        var ptSize = settings.getPortraitSize();
        var flyingFuncs = [];
        var destroyFuncs = [];
        $.each(targets, function(i, target) {
            var imgGroup = null;
            destroyFuncs.push(function() {
                imgGroup.destroy();
                delete imgGroup;
            });
            flyingFuncs.push(function() {
                var sourcePoint = { x: 0, y: 0 };
                var targetPoint = { x: 0, y: 0 };
                if (isCardSource) {
                    sourcePoint.x = sourceShape.getX() + ptSize.width / 2 - imgObj.width / 2;
                    sourcePoint.y = sourceShape.getY() + ptSize.height / 2 - imgObj.height / 2;
                } else {
                    sourcePoint.x = sourceShape.getX() - imgObj.width / 2;
                    sourcePoint.y = sourceShape.getY() - imgObj.height / 2;
                }
                if (isHeroTarget) {
                    targetPoint.x = target.getX() + target.getWidth() / 2 - imgObj.width / 2;
                    targetPoint.y = target.getY() + target.getHeight() / 2 - imgObj.height / 2;
                } else {
                    targetPoint.x = target.group.getX() + ptSize.width / 2 - imgObj.width / 2;
                    targetPoint.y = target.group.getY() + ptSize.height / 2 - imgObj.height / 2;
                }
    
                imgGroup = new Kinetic.Group({
                    x: sourcePoint.x,
                    y: sourcePoint.y,
                });
    
                var imgSrc = new Image();
                imgSrc.src = resDir + '/img/effect/' + imgObj.fileName;
                imgSrc.onload = function() {
                    var img = new Kinetic.Image({
                        x : 0,
                        y : 0,
                        image : imgSrc,
                    });
                    imgGroup.add(img);
                };
                self.stage.get('#effect-layer')[0].add(imgGroup);
                
                flyingFuncs.push(function () {
                    new Kinetic.Tween({
                        node: imgGroup,
                        x: targetPoint.x,
                        y: targetPoint.y,
                        duration: duration,
                    }).play();
                });
            });
        });
        
        this.addAnimations("flyingImages", flyingFuncs, duration);
        this.addAnimations("destroyFlyingImages", destroyFuncs, settings.minimumDuration);
    };
    
    this.animations = [];

    this.addAnimation = function(name, func, duration) {
        if ($.type(name) != 'string') {
            console.error("ERROR: first variable of this.addAnimation must be string! Given: " + name);
        }
        if (duration == undefined || duration <= 0) {
            console.error("ERROR: Invalid addAnimation duration of " + name + ": " + duration + "!");
        }
        var timeOutVar = window.setTimeout(function() {
            //console.log("ANIM Executing " + name + " (duration = " + duration + ")");
            //console.log("ANIM Next animation should happen at " + new Date(new Date().getTime() + duration * 1000));
            func();
        }, this.time * 1000);
        this.animations.push(timeOutVar);
        //console.log("ANIM Registering " + name + " (time = " + this.time + ", duration = " + duration + ")");
        this.time += duration;
    };
    
    this.addAnimations = function(name, funcs, duration) {
        this.addAnimation(name, function () {
            for (var i = 0; i < funcs.length; ++i) {
                //console.log("ANIM Executing " + name + "[" + i + "] (duration = " + duration + ")");
                //console.log("ANIM Next animation should happen at " + new Date(new Date().getTime() + duration * 1000));
                funcs[i]();
            }
        }, duration);
    };
    
    this.pauseAnimation = function(duration) {
        this.addAnimation("pause", function () {}, duration);
    };
    
    this.showSplash = function(attrs) {
        var opt = attrs || {};
        var text = opt.text || '';
        var duration = opt.duration || settings.bannerDuration;
        var pause = opt.pause || settings.bannerPause;
        var exitType = opt.exitType || 'pause';
        
        var width = settings.width;
        var splashText = this.stage.get('#splash-text')[0];
        var splashRect = this.stage.get('#splash-label')[0];
        this.addAnimation("showSplash", function() {
            splashRect.setX(-width);
            splashText.setText(text);
            splashText.centerMiddle(splashRect);
            new Kinetic.Tween({
                node: splashRect,
                x: 0,
                duration: duration,
            }).play();
            new Kinetic.Tween({
                node: splashText,
                x: width / 2 - splashText.getWidth() / 2,
                duration: duration,
            }).play();
        }, duration);
        if (exitType == 'pause') {
            this.pauseAnimation(pause);
            this.addAnimation("exitSplash", function() {
                new Kinetic.Tween({
                    node: splashRect,
                    x: width,
                    duration: duration,
                }).play();
                new Kinetic.Tween({
                    node: splashText,
                    x: width * 1.5 - splashText.getWidth() / 2,
                    duration: duration,
                }).play();
            }, duration);
        };
    };
    
    /**
     * @param adjName: AT or HP
     * @param data: the event data
     * @param addEffect (boolean): whether is to add or lost effect
     */
    this.adjustValue = function(data, adjName, addEffect) {
        var source = data[0]; // EntityRuntimeInfo
        var target = data[1]; // EntityRuntimeInfo
        var adjustment = data[2]; // int
        var newValue = data[3]; // int
        var featureName = data[4]; // String
        var ptSize = settings.getPortraitSize();
        var adjTextPrefix = addEffect ? '' : '失去\r\n';
        var adjValueText = adjustment > 0 ? '+' + adjustment : '-' + (-adjustment);
        var adjRect = null;
        var adjText = null;
        var adjGroup = null;
        var self = this;
        this.addAnimation("adjustValuePrepare", function() {
            adjRect = new Kinetic.Rect({
                x: 0, y: 0, width: ptSize.width, height: settings.adjRectHeight,
                stroke: settings.adjRectBorderColor,
                strokeWidth: settings.adjRectBorderWidth,
                fill: settings.adjRectFill,
                opacity: settings.adjRectOpacity,
            });
            adjText = new Kinetic.Text({
                x: 0, y: 0,
                text: adjTextPrefix + adjName + adjValueText + '\r\n' + featureName,
                fontFamily: settings.adjustFontFamily,
                fontSize: settings.adjustFontSize,
                fill: adjName == 'AT' ? settings.adjustAdjAtColor : settings.adjustAdjHpColor,
            });
            adjGroup = new Kinetic.Group({
                x: settings.width, y: settings.height,
            });
            adjGroup.add(adjRect).add(adjText);
            adjText.centerMiddle(adjRect);
            self.stage.get('#effect-layer')[0].add(adjGroup);
        }, settings.minimumDuration);
        var targetCard = this.getCard(target);
        if (source.type != 'Card' || source.uniqueName == target.uniqueName) {
            this.addAnimation("showAdjust" + adjName, function() {
                adjGroup.setX(targetCard.group.getX());
                adjGroup.setY(-adjRect.getHeight());
                new Kinetic.Tween({
                    node: adjGroup,
                    x: adjGroup.getX(),
                    y: targetCard.group.getY() + ptSize.height / 2 - adjRect.getHeight() / 2,
                    duration: settings.adjustDuration / 5,
                }).play();
            }, settings.adjustDuration);
        } else {
            var sourceCard = this.getCard(source);
            this.addAnimation("showAdjust" + adjName, function() {
                adjGroup.setX(sourceCard.group.getX());
                adjGroup.setY(sourceCard.group.getY() + ptSize.height / 2 - adjRect.getHeight() / 2);
                new Kinetic.Tween({
                    node: adjGroup,
                    x: targetCard.group.getX(),
                    y: targetCard.group.getY() + ptSize.height / 2 - adjRect.getHeight() / 2,
                    duration: settings.adjustDuration,
                }).play();
            }, settings.adjustDuration);
        }

        this.addAnimation("destroyAdjust" + adjName + "Text", function() {
            var layer = adjGroup.getLayer();
            adjGroup.destroy();
            delete adjGroup;
            layer.draw();
        }, settings.minimumDuration);
        
        this.addAnimation('set' + adjName + 'Text', function() {
            // Adjust text number
            var textShape = adjName == 'AT' ? targetCard.atText : targetCard.hpText;
            var rectShape = adjName == 'AT' ? targetCard.atRect : targetCard.hpRect;
            textShape.setText(adjName + ': ' + newValue);
            textShape.centerMiddle(rectShape);
            textShape.getLayer().draw();
        }, settings.minimumDuration);
    };
    
    this.updateCardHP = function(card, hp, maxHP) {
        this.addAnimation('updateCardHp', function () {
            card.hpText.setText('HP: ' + hp);
            card.hpText.centerMiddle(card.hpRect);
            new Kinetic.Tween({
                node: card.hpBar,
                width: hp * card.hpRect.getWidth() / maxHP,
                duration: settings.updateHpDuration,
            }).play();
        }, settings.updateHpDuration);
    };
    
    /**
     * @attribute name (required)
     * @attribute cardShape (required)
     * @attribute text (default to '')
     * @attribute textColor
     * @attribute duration
     */
    this.displayCardMsg = function(attrs) {
        if (!attrs || !attrs.name || !attrs.cardShape) {
            console.error("Invalid attrs in this.displayCardMsg: " + attrs);
            return;
        }
        var self = this;
        var opt = $.extend({
            textColor: settings.cardMsgTextColor,
            text: '',
            duration: settings.cardMsgDuration,
        }, (attrs || {}));
        var ptSize = settings.getPortraitSize();
        var group = null;
        this.addAnimation(name, function () {
            var rect = new Kinetic.Rect({
                x: 0, y: 0, width: ptSize.width,
                height: settings.cardMsgRectHeight,
                stroke: settings.cardMsgRectBorderColor,
                strokeWidth: settings.cardMsgRectBorderWidth,
                fill: settings.cardMsgRectFill,
                opacity: settings.cardMsgRectOpacity,
            });
            var text = new Kinetic.Text({
                text: opt.text,
                fontFamily: settings.cardMsgFontFamily,
                fontSize: settings.cardMsgFontSize,
                fill: opt.textColor,
            });
            group = new Kinetic.Group({
                x: opt.cardShape.getX(),
                y: opt.cardShape.getY() + ptSize.height / 2 - rect.getHeight() / 2,
            });
            group.add(rect).add(text);
            self.stage.get('#effect-layer')[0].add(group);
            text.centerMiddle(rect);
            group.getLayer().draw();
        }, opt.duration);
        this.addAnimation(name + "Clearup", function() {
            var layer = group.getLayer();
            group.destroy();
            delete group;
            layer.draw();
        }, settings.minimumDuration);
    };
    
    /**
     * @param attacker { ownerId, uniqueName }
     * @param defender
     * { ownerId, uniqueName } if attackingHero is false
     * { id } if attackingHero is true
     */
    this.normalAttack = function(attacker, defender, attackingHero) {
        this.flyImage(
                { fileName: 'sword.png', width: settings.swordWidth, height: settings.swordHeight },
                attacker,
                attackingHero ? defender : [ defender ]
        );
        return;
        
        var ptSize = settings.getPortraitSize();
        var self = this;
        if (attacker.type != 'Card') {
            return;
        }
        var attackerCard = this.getCard(attacker);
        if (attackerCard == null) {
            console.error('Cannot find card ' + attacker.uniqueName);
        }
        var defenderCard = null;
        if (!attackingHero) {
            defenderCard = this.getCard(defender);
        }
        var sword = null;
        this.addAnimation("normalAttack", function() {
            sword = Arena.createSword();
            self.stage.get('#effect-layer')[0].add(sword);
            // Center sword inside attacker portrait
            sword.setX(attackerCard.group.getX() + ptSize.width / 2 - settings.swordWidth / 2);
            sword.setY(attackerCard.group.getY() + ptSize.height / 2 - settings.swordHeight / 2);
            var targetPoint = { x: 0, y: 0 };
            if (attackingHero) {
                var hpBgRect = self.__getShape(defender, 'hpbg-rect');
                targetPoint.x = hpBgRect.getX() + hpBgRect.getWidth() / 2 - settings.swordWidth / 2;
                targetPoint.y = hpBgRect.getY() + hpBgRect.getHeight() / 2 - settings.swordHeight / 2;
            } else {
                targetPoint.x = defenderCard.group.getX() + ptSize.width / 2 - settings.swordWidth / 2;
                targetPoint.y = defenderCard.group.getY() + ptSize.height / 2 - settings.swordHeight / 2;
            }
            new Kinetic.Tween({
                node: sword,
                x: targetPoint.x,
                y: targetPoint.y,
                duration: settings.normalAttackDuration,
            }).play();
        }, settings.normalAttackDuration);
        this.addAnimation("normalAttackDestroySword", function() {
            sword.destroy();
            delete sword;
        }, settings.minimumDuration);
    };
    
    /**
     * @param attacker {
     *    ownerId, type, uniqueName
     * }
     */
    this.zoomAttacker = function(attacker, zoomOut) {
        if (attacker.type == 'Card') {
            var card = this.arenas[attacker.ownerId].fields.ofName(attacker.uniqueName);
            this.addAnimation("zoomAttacker", function() {
                var shape = card.group;
                var deltaY = settings.getPortraitSize().height * settings.zoomRate;
                new Kinetic.Tween({
                    node: shape,
                    y: zoomOut ? shape.getY() - deltaY : shape.getY() + deltaY,
                    duration: settings.zoomAttackerDuration,
                }).play();
            }, settings.zoomAttackerDuration);
        }
    };

    this.start = function(animation) {
        settings.refreshSize();
        $.each(this.animations, function(i, v) {
            clearTimeout(v);
        });
        this.time = this.initTime;
        $('#battle-canvas').width(this.width).height(this.height);
        this.stage = new Kinetic.Stage({
            container : 'battle-canvas',
            width : settings.width,
            height : settings.height
        });
        var bgLayer = new Kinetic.Layer();
        var bg = new Kinetic.Rect({
            id: 'bg-layer',
            x : 0,
            y : 0,
            width : settings.width,
            height : settings.height,
            fill : settings.bgColor,
            strokeWidth : 0,
        });
        bgLayer.add(bg);
        this.stage.add(bgLayer);
        
        var events = animation.events;
        for (var i = 0; i < events.length; ++i) {
            var event = events[i];
            console.log(JSON.stringify(event));
            var handlerName = "__" + event.name;
            var handler = this[handlerName];
            if (handler) {
                handler.apply(this, [event.dataList]);
            } else {
                console.log("ERROR: unknown event: " + event.name);
            }
        }
    };
};
var animater = null;

var showBattle = function(animation) {
    animater.start(animation);
};

$(document).ready( function() {
    animater = new Animater();
});