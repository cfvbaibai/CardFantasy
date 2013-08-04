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
    this.runeFill = { FIRE: '#FF0000', WIND: '#00FF00', WATER: '#0000FF', GROUND: '#FF9900' };
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
    
    this.cardHpRectBorderColor = 'white';
    this.cardHpRectBorderWidth = 1;
    this.cardHpRectFill = '#222222';
    this.cardHpRectOpactiy = '0.8';
    this.cardHpRectHeight = 15;
    this.cardHpTextColor = 'white';
    
    this.cardAtRectBorderColor = this.cardHpRectBorderColor;
    this.cardAtRectBorderWidth = this.cardHpRectBorderWidth;
    this.cardAtRectFill = this.cardHpRectFill;
    this.cardAtRectOpacity = this.cardHpRectOpactiy;
    this.cardAtRectHeight = this.cardHpRectHeight;
    this.cardAtTextColor = this.cardHpTextColor;
    
    this.drawCardDuration = 0.5;
    
    this.summonCardDuration = 0.5;
    
    this.hpChangeDuration = 0.7;

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

Array.prototype.removeOfName = function(name) {
    for (var i = 0; i < this.length; ++i) {
        if (this[i].name == name) {
            return this.splice(i, 1)[0];
        }
    }
    return null;
};

var Arena = function(playerId, playerNumber) {
    this.playerId = playerId, playerNumber;
    this.playerNumber = playerNumber;
    this.hands = [];

    this.fields = [];
    this.runes = [];
    
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
        return group;
    };
    
    this.createPortrait = function(card) {
        var size = settings.getPortraitSize();
        var group = new Kinetic.Group({ x: settings.width, y: settings.height, });
        var cardAvatar = new Image();
        cardAvatar.src = resDir + '/img/cardportrait/' + card.id + '.jpg';
        var hpRect = new Kinetic.Rect({
            x: 0,
            y: size.height - settings.cardAtRectHeight - settings.cardHpRectHeight,
            width: size.width,
            height: settings.cardHpRectHeight,
            stroke: settings.cardHpRectBorderColor,
            strokeWidth: settings.cardHpRectBorderWidth,
            fill: settings.cardHpRectFill,
            opacity: settings.cardHpRectOpacity,
        });
        var atRect = new Kinetic.Rect({
            x: 0,
            y: size.height - settings.cardAtRectHeight,
            width: size.width,
            height: settings.cardAtRectHeight,
            stroke: settings.cardAtRectBorderColor,
            strokeWidth: settings.cardAtRectBorderWidth,
            fill: settings.cardAtRectFill,
            opacity: settings.cardAtRectOpacity,
        });
        var hpText = new Kinetic.Text({
            text: 'HP: ' + card.hp, fill: settings.cardHpTextColor,
        }).centerMiddle(hpRect);
        var atText = new Kinetic.Text({
            text: 'AT: ' + card.at, fill: settings.cardAtTextColor,
        }).centerMiddle(atRect);
        cardAvatar.onload = function() {
            var cardAvatarImage = new Kinetic.Image({
                x : 2,
                y : 2,
                width: size.width - 4,
                height: size.height - 4,
                image : cardAvatar,
            });
            group.add(cardAvatarImage).add(hpRect).add(atRect).add(hpText).add(atText);
        };
        this.fields.push({
            name: card.name, group: group,
            hpRect: hpRect, atRect: atRect, hpText: hpText, atText: atText });
        return group;
    };
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
                id: pos.id,
                x: pos.x,
                y: pos.y,
                radius: settings.getRuneRadius(),
                stroke: settings.runeCircleStroke,
                strokeWidth: settings.runeCircleStrokeWidth,
                fill: settings.runeCircleFill,
            }));
        }
    };
    
    this.__getPrefix = function(player) {
        return player.number == 0 ? 'hero1-' : 'hero2-';
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
        this.addAnimation(function() {
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
    
    this.__updateRunes = function(player, runeInitInfos) {
        for (var i = 0; i < runeInitInfos.length; ++i) {
            var runeInitInfo = runeInitInfos[i];
            var runeShape = this.__getShape(player, 'rune' + i);
            runeShape.setFill(settings.runeFill[runeInitInfo.type]);
            runeShape.getLayer().draw();
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
        var funcs = [];
        for (var i = 0; i < fields.length; ++i) {
            (function(iCard, card) {
                var pos = settings.getPortraitPos(arena.playerNumber, iCard);
                funcs.push(function() {
                    new Kinetic.Tween({
                        node: card.group,
                        x: pos.x,
                        y: pos.y,
                        duration: settings.summonCardDuration,
                    }).play();
                });
            })(i, fields[i]);
        }
        this.addAnimations(funcs, settings.summonCardDuration);
    };
    
    this.__roundEnded = function(data) {
        var stage = this.stage;
        var roundText = stage.get('#round-text')[0];
        this.addAnimation(function() {
            roundText.setText("回合: " + data[0]);
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
        this.addAnimations(funcs, 0.3);
    };
    
    this.__cardDrawed = function(data) {
        var playerId = data[0];
        var arena = this.arenas[playerId];
        var targetIndex = arena.hands.length;
        var pn = arena.playerNumber;
        var logo = arena.createLogo(data[1], data[2], data[3], settings.getLogoSize());
        this.stage.get('#card-layer')[0].add(logo);
        this.addAnimation(function() {
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

        for (var iHand = 0; iHand < arena.hands.length; ++iHand) {
            (function(i, currentCard) {
                var card = currentCard;
                if (card == logo) { return; }
                var pos = settings.getLogoPos(arena.playerNumber, i);
                handFuncs.push(function () {
                    new Kinetic.Tween({
                        node: card.group,
                        x: pos.x,
                        y: pos.y,
                        duration: settings.drawCardDuration,
                    }).play();
                });
            })(iHand, arena.hands[iHand]);
        }
        this.addAnimations(handFuncs, settings.drawCardDuration);

        this.addAnimation(function() {
            logo.group.destroy();
            delete logo;
        }, settings.minimumDuration);

        var targetIndex = arena.fields.length;
        var portrait = arena.createPortrait(card, settings.getPortraitSize());
        this.stage.get('#card-layer')[0].add(portrait);
        this.addAnimation(function() {
            var pos = settings.getPortraitPos(arena.playerNumber, targetIndex);
            new Kinetic.Tween({
                node: portrait,
                x: pos.x,
                y: pos.y,
                duration: settings.summonCardDuration,
            }).play();
        }, settings.summonCardDuration);
    };
    
    this.__attackHero = function(data) {
        data[0].hp -= data[1];
        if (data[0].hp < 0) {
            data[0].hp = 0;
        }
        this.__updateHeroHp(data[0]);
    };
    
    this.__returnCard = function(data) {
        var playerId = data[2];
        var arena = this.arenas[playerId];
        var card = arena.fields.removeOfName(data[3].name);
        this.addAnimation(function() {
            new Kinetic.Tween({
                node: card.group,
                x: settings.width,
                duration: settings.summonCardDuration,
            }).play();
        }, settings.summonCardDuration);
        this.addAnimation(function() {
            card.group.destroy();
            delete card;
        }, settings.minimumDuration);
    };
    
    this.__cardDead = function(data) {
        var playerId = data[0];
        var arena = this.arenas[playerId];
        var card = arena.fields.removeOfName(data[1].name);
        this.addAnimation(function() {
            card.hpText.setText('DEAD');
            card.hpText.setFill('red');
            card.hpText.centerMiddle(card.hpRect);
            new Kinetic.Tween({
                node: card.group,
                x: settings.width,
                duration: settings.summonCardDuration,
            }).play();
        }, settings.summonCardDuration);
        this.addAnimation(function() {
            card.group.destroy();
            delete card;
        }, settings.minimumDuration);
    };
    
    this.__cardToGrave = function(data) {
        
    };
    
    this.__gameEnded = function(data) {
        var player = data[0];
        /*
        var loser = data[1];
        var cause = data[2];
        if (cause == '英雄死亡') {
            loser.hp = 0;
            this.__updateHeroHp(loser);
        }
        */
        this.showSplash({ text: '战斗结束!\r\n获胜者: ' + player.id + '\r\n共造成伤害: ' + data[3], });
        this.showSplash({ text: '这个功能还没完成，\r\n就做了那么点儿，\r\n白白会努力做的！^0^', exitType: 'onclick', });
    };

    this.addAnimation = function(func, duration) {
        window.setTimeout(func, this.time * 1000);
        this.time += duration;
    };
    
    this.addAnimations = function(funcs, duration) {
        window.setTimeout(function() {
            for (var i = 0; i < funcs.length; ++i) {
                funcs[i]();
            }
        }, this.time * 1000);
        this.time += duration;
    };
    
    this.pauseAnimation = function(duration) {
        this.addAnimation(function () {}, duration);
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
        this.addAnimation(function() {
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
            this.addAnimation(function() {
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
    
    this.start = function(animation) {
        settings.refreshSize();
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
            if (false) {
            } else if (event.name == 'stage-created') {
                this.__stageCreated(event.data);
            } else if (event.name == 'player-added') {
                this.__playerAdded(event.data);
            } else if (event.name == 'game-start') {
                this.__gameStarted(event.data);
            } else if (event.name == 'round-started') {
                this.__roundStarted(event.data);
            } else if (event.name == 'card-drawed') {
                this.__cardDrawed(event.data);
            } else if (event.name == 'summon-card') {
                this.__summonCard(event.data);
            } else if (event.name == 'attack-hero') {
                this.__attackHero(event.data);
            } else if (event.name == 'return-card') {
                this.__returnCard(event.data);
            } else if (event.name == 'card-dead') {
                this.__cardDead(event.data);
            } else if (event.name == 'card-to-grave') {
                this.__cardToGrave(event.data);
            } else if (event.name == 'compact-field') {
                this.__compactField(event.data);
            } else if (event.name == 'round-ended') {
                this.__roundEnded(event.data);
            } else if (event.name == 'game-ended') {
                this.__gameEnded(event.data);
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