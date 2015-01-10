CardFantasy.BattleAnimation = {};

// OUTERMOST IIFE
(function (BattleAnimation) {

(function($) {
    /*
     * Changes the displayed text for a jquery mobile button.
     * Encapsulates the idiosyncracies of how jquery re-arranges the DOM
     * to display a button for either an <a> link or <input type="button">
     */
    $.fn.changeButtonText = function(newText) {
        return this.each(function() {
            var $this = $(this);
            if( $this.is('a') ) {
                $('span.ui-btn-text', $this).text(newText);
                return;
            }
            if( $this.is('input') ) {
                $this.val(newText);
                // go up the tree
                var ctx = $this.closest('.ui-btn');
                $('span.ui-btn-text', ctx).text(newText);
                return;
            }
        });
    };
})(jQuery);

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

Array.prototype.remove = function(obj) {
    var index = -1;
    for (var i = 0; i < this.length; ++i) {
        if (this[i] == obj) {
            index = i;
            break;
        }
    }
    if (index >= 0) {
        this.removeAt(index, index);
    }
};

Array.prototype.removeAt = function(from, to) {
    var rest = this.slice((to || from) + 1 || this.length);
    this.length = from < 0 ? this.length + from : from;
    return this.push.apply(this, rest);
};

var CfSize = function(attr) {
    this.width = attr.width;
    this.height = attr.height;
};

var CfPos = function(attr) {
    this.x = attr.x;
    this.y = attr.y;
};

var ArenaSettings = function() {
    this.fontSizeFactor = $.browser.webkit ? 1 : 1.4;
    this.maxWidth = 495;
    this.heightRate = 1.0;
    this.width = this.maxWidth;
    this.height = this.width * this.heightRate;

    this.minimumDuration = 0.01;
    this.bannerDuration = 0.4;
    this.updateHpDuration = 0.5;
    this.drawCardDuration = 0.2;
    this.compactFieldDuration = 0.1;
    this.reincarnateDuration = 0.5;
    this.transportDuration = 0.5;
    this.summonCardDuration = 0.2;
    this.summonCardPause = 0.5;
    this.hpChangeDuration = 0.3;
    this.flyImageDuration = 0.3;
    this.skillDuration = 0.7;
    this.adjustDuration = 0.7;
    this.cardMsgDuration = 0.7;
    this.skillSplashDuration = 0.1;
    this.skillSplashPause = 0.7;

    this.bgColor = '#222200';
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
    this.runeFontFamily = this.fontFamily;
    this.runeFontSize = 8 * this.fontSizeFactor;
    this.runeTextColor = 'white';

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
    this.bannerPause = 1.3;
    
    this.handDelayFontSize = 10 * this.fontSizeFactor;
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
    this.cardHpFontSize = 8 * this.fontSizeFactor;
    this.cardHpTextColor = 'white';
    this.cardHpBarOpacity = this.cardHpRectOpacity;
    this.cardHpBarFill = 'red';

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

    this.adjustFontFamily = this.fontFamily;
    this.adjustFontSize = 8 * this.fontSizeFactor;
    this.addAtColor = 'blue';
    this.reduceAtColor = 'brown';
    this.addHpColor = 'green';
    this.reduceHpColor = 'red';
    this.adjRectHeight = 50;
    this.adjRectBorderColor = 'white';
    this.adjRectBorderWidth = 1;
    this.adjRectFill = '#CCCCCC';
    this.adjRectOpacity = 0.95;
    this.loseAdjAtColor = '#222222';
    this.loseAdjHpColor = '#222222';
    
    this.cardMsgRectHeight = 50;
    this.cardMsgRectBorderColor = 'white';
    this.cardMsgRectBorderWidth = 1;
    this.cardMsgRectFill = '#CCCCCC';
    this.cardMsgRectOpacity = 0.95;
    this.cardMsgFontFamily = this.fontFamily;
    this.cardMsgFontSize = 8 * this.fontSizeFactor;
    this.cardMsgTextColor = 'black';

    this.attackCardTextColor = 'red';
    this.healCardTextColor = 'green';

    this.skillTipBgColor = '#CCCCCC';
    this.skillTipOpacity = 0.8;
    this.skillTipPointerWidth = 5;
    this.skillTipPointerHeight = 10;
    this.skillTipShadowColor = 'black';
    this.skillTipShadowBlur = 10;
    this.skillTipShadowOffset = 5;
    this.skillTipShadowOpacity = 0.5;
    this.skillTipFontFamily = this.fontFamily;
    this.skillTipFontSize = 8 * this.fontSizeFactor;
    this.skillTipPadding = 10;
    this.skillTipTextColor = 'blue';
    
    this.player1HpRectY = 0;

    this.refreshSize = function () {
        var currentWidth = $(window).width() * 0.95;
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
        return new CfSize({ width: this.getRightPanelWidth() / 5, height: this.getPanelHeight() * this.handHeightRate });
    };
    
    this.getPortraitSize = function() {
        return new CfSize({
            width: this.getRightPanelWidth() / 5,
            height: this.getPanelHeight() * (1 - this.handHeightRate) / 2,
        });
    };
    
    this.getHeroHpBgRectSize = function() {
        return new CfSize({
            width: this.getLeftPanelClientWidth(),
            height: this.getTopRuneRectY() - this.globalPadding - this.player1HpRectY,
        });
    };
    
    this.getLogoX = function(playerNumber, logoIndex) {
        return this.getRightPanelX() + logoIndex * this.getLogoSize().width;
    };
    this.getLogoY = function(playerNumber) {
        return playerNumber == 0 ? 0 : this.height - this.getHandPanelHeight();
    };
    this.getLogoPos = function(playerNumber, logoIndex) {
        return new CfPos({ x: this.getLogoX(playerNumber, logoIndex), y: this.getLogoY(playerNumber), });
    };
    
    this.getPortraitPos = function(playerNumber, logoIndex) {
        if (logoIndex >= 0 && logoIndex <= 4) {
            return new CfPos({
                x: this.getRightPanelX() + logoIndex * this.getLogoSize().width,
                y: playerNumber == 1 ?
                        this.getPanelHeight() :
                        this.getHandPanelHeight() + this.getPortraitSize().height,
            });
        }
        var index = logoIndex - 5;
        return new CfPos({
            x: this.getRightPanelX() + index * this.getLogoSize().width,
            y: playerNumber == 1 ?
                    this.getPanelHeight() + this.getPortraitSize().height :
                    this.getHandPanelHeight(),
        });
    };
};
var settings = new ArenaSettings();

var Card = function(attr) {
    $.extend(this, attr);
    this.statusList = [];
};

var Arena = function(playerId, playerNumber) {
    this.playerId = playerId, playerNumber;
    this.playerNumber = playerNumber;
    this.hands = [];

    this.fields = [];
    this.runes = {};
    
    this.createLogo = function(id, name, delay, size) {
        var logoSize = settings.getLogoSize();
        var group = new Kinetic.Group({ x: settings.width, y: settings.height });
        group.getSize = function() { return settings.getLogoSize(); };
        
        var delayRect = new Kinetic.Rect({
            x: size.width - settings.handDelayRectSize - 1,
            y: size.height - settings.handDelayRectSize - 1,
            width: settings.handDelayRectSize,
            height: settings.handDelayRectSize,
            stroke: settings.handDelayRectBorderColor,
            strokeWidth: settings.handDelayRectBorderWidth,
            fill: settings.handDelayRectFill,
            opacity: settings.handDelayRectOpacity,
            listening: false,
        });
        var delayText = new Kinetic.Text({
            text: delay.toString(),
            fontFamily: settings.handDelayFontFamily,
            fontSize: settings.handDelayFontSize,
            fill: settings.handDelayColor,
            listening: false,
        }).centerMiddle(delayRect);

        var cardAvatar = new Image();
        cardAvatar.src = resDir + '/img/cardlogo/' + id + '.jpg';
        cardAvatar.onload = function() {
            var cardAvatarImage = new Kinetic.Image({
                id: 'LG-' + name,
                x: 2,
                y: 2,
                width: size.width - 4,
                height: size.height - 4,
                image: cardAvatar,
            });
            group.add(cardAvatarImage);
            var cardFrame = new Image();
            cardFrame.src = resDir + '/img/frame/frame.png';
            cardFrame.onload = function() {
                var cardFrameImage = new Kinetic.Image({
                    x: 0,
                    y: 0,
                    width: size.width,
                    height: size.height,
                    image: cardFrame,
                    listening: false,
                });
                group.add(cardFrameImage);
                group.add(delayRect);
                group.add(delayText);
            };
        };

        this.hands.push({ name: name, group: group, delay: delay, delayText: delayText,
            width: logoSize.width, height: logoSize.height, });
        var layer = new Kinetic.Layer({ id: 'LL' + name });
        layer.on('click', function(evt) {
            console.log('LL' + name + ' clicked');
        });
        layer.add(group);
        return group;
    };
    
    this.createPortrait = function(card) {
        var size = settings.getPortraitSize();
        var group = new Kinetic.Group({ id: 'PG-' + card.name, x: settings.width, y: settings.height,
            width: size.width, height: size.height });
        group.getSize = function() { return settings.getPortraitSize(); };
        var frame = new Kinetic.Rect({
            x: 0, y: 0, width: size.width, height: size.height,
            stroke: settings.portraitFrameColor,
            strokeWidth: settings.portraitFrameWidth,
            opacity: 0,
            listening: false,
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
            listening: false,
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
            listening: false,
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
            listening: false,
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
            listening: false,
        });
        var hpText = new Kinetic.Text({
            text: 'HP: ' + card.hp,
            fontFamily: settings.cardHpFontFamily,
            fontSize: settings.cardHpFontSize,
            fill: settings.cardHpTextColor,
            listening: false,
        }).centerMiddle(hpRect);
        var atText = new Kinetic.Text({
            text: 'AT: ' + card.at,
            fontFamily: settings.cardAtFontFamily,
            fontSize: settings.cardAtFontSize,
            fill: settings.cardAtTextColor,
            listening: false,
        }).centerMiddle(atRect);
        
        var statusRect = new Kinetic.Rect({
            x: 0, y: 0, width: size.width, height: settings.statusRectHeight + adjustY,
            stroke: settings.statusRectBorderColor,
            strokeWidth: settings.statusRectBorderWidth,
            fill: settings.statusRectFill,
            opacity: settings.statusRectOpacity,
            listening: false,
        });
        var statusText = new Kinetic.Text({
            text: '',
            fontFamily: settings.statusFontFamily,
            fontSize: settings.statusFontSize,
            fill: settings.statusTextColor,
            listening: false,
        }).centerMiddle(statusRect);
        cardAvatar.onload = function() {
            var cardAvatarImage = new Kinetic.Image({
                id: 'PT-' + card.name,
                x: 2,
                y: 2,
                width: size.width - 4,
                height: size.height - 4,
                image: cardAvatar,
            });
            group.add(cardAvatarImage).add(hpRect).add(hpBar).add(hpBarFrame).add(atRect);
            group.add(hpText).add(atText).add(statusRect).add(statusText).add(frame);
        };
        this.fields.push(new Card({
            name: card.name, group: group, frame: frame,
            hpRect: hpRect, hpBar: hpBar, atRect: atRect, statusRect: statusRect,
            hpText: hpText, atText: atText, statusText: statusText,
            width: size.width, height: size.height,
        }));
        var layer = new Kinetic.Layer({ id: 'LP' + card.name });
        layer.on('click', function(evt) {
            console.log('LP' + card.name + ' clicked');
        });
        layer.add(group);
        return group;
    };
};

var Animation = function(name, funcs, duration) {
    var me = this;
    if ($.type(name) != 'string') {
        console.error("ERROR: Invalid name: " + name);
    }
    me.name = name || 'UNKNOWN';
    me.funcs = [];
    if ($.type(funcs) == 'array') {
        $.each(funcs, function(i, func) {
            me.funcs.push(func);
        });
    } else {
        me.funcs.push(funcs);
    }
    if (duration == undefined) {
        me.duration = settings.minimumDuration;
    } else if (isNaN(duration) || duration <= 0) {
        console.error("ERROR: Invalid duration: " + duration);
        me.duration = settings.minimumDuration;
    } else {
        me.duration = duration;
    }
    
    this.play = function(speed) {
        $.each(me.funcs, function(i, func) {
            func(speed, duration);
        });
    };
};

Animation.pause = function(duration) {
    return new Animation('pause', function() {}, duration);
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
                fontFamily: settings.runeFontFamily,
                fontSize: settings.runeFontSize,
                fill: settings.runeTextColor,
            }));
            layer.add(new Kinetic.Text({
                id: pos.id + '-energy',
                fontFamily: settings.runeFontFamily,
                fontSize: settings.runeFontSize,
                fill: settings.runeTextColor,
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

    this.updateHeroHp = function(player) {
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
        var me = this;
        this.addAnimation('updateRune', function () {
            var arena = me.arenas[player.id];
            var i = arena.runes[runeInfo.name];
            var runeCircleShape = me.__getShape(player, 'rune' + i + '-circle');
            runeCircleShape.setFill(settings.runeFill[runeInfo.type]);
            var runeNameShape = me.__getShape(player, 'rune' + i + '-name');
            runeNameShape.setText(runeInfo.name);
            runeNameShape.setFill(settings.runeTextFill[runeInfo.type]);
            runeNameShape.setX(runeCircleShape.getX() - runeNameShape.getWidth() / 2);
            runeNameShape.setY(runeCircleShape.getY() - runeNameShape.getHeight() + 2);
            var runeEnergyShape = me.__getShape(player, 'rune' + i + '-energy');
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
        frameLayer.on('click', function() { console.log('frame-layer clicked'); });
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
        settings.player1HpRectY = hp1RectY; 
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
        cardLayer.on('click', function(evt) { console.log('card-layer ' + evt.targetNode.getId() + ' clicked'); });
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
        var effectLayer = new Kinetic.Layer({ id: 'effect-layer', listening: false, });
        effectLayer.on('click', function(evt) { console.log('effect-layer ' + evt.targetNode.getId() + ' clicked'); });
        this.stage.add(effectLayer);

        var roundLayer = new Kinetic.Layer({ id: 'round-layer' });
        roundLayer.on('click', function(evt) { console.log('round-layer ' + evt.targetNode.getId() + ' clicked'); });
        var roundRect = new Kinetic.Rect({
            id: 'round-rect', x: 0, y: 0, width: settings.width, height: settings.height, opacity: 0, listening: false,
        });
        roundLayer.add(roundRect);
        var roundText = new Kinetic.Text({
            id: 'round-text',
            text: '回合: 1',
            fontSize: settings.roundFontSize,
            fontFamily: settings.roundFontFamily,
            fill: settings.roundTextColor,//'yellow',
            opacity: settings.roundTextOpacity,//0.4,
            listening: false,
        });
        roundLayer.add(roundText);
        this.stage.add(roundLayer);
        roundText.centerMiddle(roundRect);
        roundLayer.draw();
        
        var splashLayer = new Kinetic.Layer({ id: 'splash-layer' });
        splashLayer.on('click', function() { console.log('splash-layer clicked'); });
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

        this.updateHeroHp(player, player.maxHP, player.maxHP);
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

    this.clearStatus = function(arena, statusToRemove) {
        var me = this;
        $.each(arena.fields, function (i, card) {
            me.removeStatusFromCard(card, statusToRemove);
        });
    };
    
    this.removeStatusFromCard = function(card, statusToRemove) {
        var me = this;
        var newStatusList = [];
        if (!card) { return; }
        for (var i = 0; i < card.statusList.length; ++i) {
            var cardStatus = card.statusList[i];
            if (statusToRemove.indexOf(cardStatus) < 0) {
                newStatusList.push(cardStatus);
            }
        }
        card.statusList = newStatusList;
        var newText = newStatusList.join(); 
        me.addAnimation("clearStatus", function() {
            card.statusText.setText(newText);
            card.statusText.centerMiddle(card.statusRect);
            card.group.getLayer().draw();
        }, settings.minimumDuration);
    };
    
    this.__roundEnded = function(data) {
        var round = data[0];
        //var playerId = data[1];
        var stage = this.stage;
        var roundText = stage.get('#round-text')[0];
        
        // DELETED: Remove all debuffs.
        // Now status is removed by __removeStatus event handler.
        /*
        var me = this;
        $.each(this.arenas, function(i, arena) {
            me.clearStatus(arena, ['虚']);
        });
        this.clearStatus(this.arenas[playerId], ['冻', '麻', '锁', '毒', '惑']);
        */
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
    
    this.__increaseSummonDelay = function(data) {
        var cardRtInfo = data[0];
        var offset = data[1];
        var card = this.getCard(cardRtInfo);
        if (card) {
            this.addAnimation("increaseSummonDelay", function() {
                card.delay += offset;
                card.delayText.setText(card.delay.toString());
                card.delayText.getLayer().draw();
            }, settings.minimumDuration);
        }
    }
    
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
        if (logo != null) {
            // Revived cards does not have a logo.
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
        }

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
        this.addPause(settings.summonCardPause);
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
                if (card.frame) {
                    card.frame.setOpacity(0);
                    card.frame.getLayer().draw();
                }
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
    
    this.overlapStatus = ['燃', '毒'];
    this.__addCardStatus = function(data) {
        //var attacker = data[0];
        var defender = data[1];
        var skillName = data[2];
        var longStatus = data[3];
        var shortStatus = data[4];
        var defenderCard = this.getCard(defender, true);
        if (defenderCard == null) {
            // 裂伤再死亡之后发动就有可能造成null
            return;
        }

        if (this.overlapStatus.indexOf(shortStatus) >= 0 || defenderCard.statusList.indexOf(shortStatus) < 0) {
            this.displayCardMsg({
                name: 'addCardStatus',
                cardShape: defenderCard.group,
                text: skillName + '\r\n\r\n导致\r\n\r\n' + longStatus, 
            });
            defenderCard.statusList.push(shortStatus);
        }
        var text = defenderCard.statusList.join();
        this.addAnimation('updateCardStatus', function() {
            defenderCard.statusText.setText(text);
            defenderCard.statusText.centerMiddle(defenderCard.statusRect);
            defenderCard.group.getLayer().draw();
        }, settings.minimumDuration);
    };
    
    this.__removeCardStatus = function(data) {
        var cardData = data[0];
        var shortStatus = data[1];
        var card = this.getCard(cardData, true);
        if (card == null) {
            return;
        }
        this.removeStatusFromCard(card, shortStatus);
    };
    
    this.__debuffDamage = function(data) {
        var cardRtInfo = data[0];
        var cardStatus = data[1];
        var damage = data[2];
        var currentHP = data[3];
        var maxHP = data[4];
        var card = this.getCard(cardRtInfo);
        this.displayCardMsg({
            name: 'debuffDamage',
            cardShape: card.group,
            text: cardStatus + '\r\n\r\n造成伤害\r\n\r\n' + damage,
            textColor: 'red',
        });
        this.updateCardHP(card, currentHP, maxHP);
    };
    
    this.__attackHero = function(data) {
        // var attacker = data[0];
        var defenderHero = data[1];
        var damage = data[2];
        var skillName = data[3];
        this.displayCardMsg({
            name: 'attackHeroMsg',
            cardShape: this.__getShape(defenderHero, 'hpbg-rect'),
            textColor: settings.attackCardTextColor,
            text: '伤害: ' + damage + '\r\n' + skillName,
            size: settings.getHeroHpBgRectSize(),
        });
        defenderHero.hp -= damage;
        if (defenderHero.hp < 0) {
            defenderHero.hp = 0;
        }
        this.updateHeroHp(defenderHero);
    };
    
    this.__attackCard = function(data) {
        //var attacker = data[0];
        var defender = data[1];
        var skillName = data[2];
        var damage = data[3];
        var currentHP = data[4];
        var maxHP = data[5];
        var dfCard = this.getCard(defender);
        this.displayCardMsg({
            name: 'attackCard',
            cardShape: dfCard.group,
            textColor: settings.attackCardTextColor,
            text: '伤害: ' + damage + '\r\n' + skillName,
        });
        this.updateCardHP(dfCard, currentHP, maxHP);
    };
    
    this.__healHero = function(data) {
        //var healer = data[0];
        var healee = data[1];
        var skillName = data[2];
        var heal = data[3];
        var currentHP = data[4];
        this.displayCardMsg({
            name: 'healHeroMsg',
            cardShape: this.__getShape(healee, 'hpbg-rect'),
            textColor: settings.healCardTextColor,
            text: '治疗: ' + heal + '\r\n' + skillName,
        });
        healee.hp = currentHP;
        this.updateHeroHp(healee);
    };

    this.__healCard = function(data) {
        //var healer = data[0];       // EntityRuntimeInfo
        var healee = data[1];       // EntityRuntimeInfo
        var skillName = data[2];  // String
        var heal = data[3];         // int
        var currentHP = data[4];    // int
        var maxHP = data[5];
        var healeeCard = this.getCard(healee);
        this.displayCardMsg({
            name: 'healCard',
            cardShape: healeeCard.group,
            textColor: settings.healCardTextColor,
            text: '治疗: ' + heal + '\r\n' + skillName,
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
        var healSkill = data[2];
        //var blockSkill = data[3];
        var healeeCard = this.getCard(healee);
        this.displayCardMsg({
            name: 'healBlocked',
            cardShape: healeeCard.group,
            text: healSkill + '\r\n无效',
        });
    };

    this.__blockDamage = function(data) {
        //var protector = data[0];
        //var attacker = data[1];
        var defender = data[2];
        var skillName = data[3];
        var originalDamage = data[4];
        var actualDamage = data[5];
        /*
        if (protector.uniqueName != defender.uniqueName) {
            console.error("Protector(" + protector.uniqueName + ") != Defender(" +
                    defender.uniqueName + "). Not supported yet!");
            return;
        }
        */
        if (skillName == '闪避') {
            return;
        }
        if (originalDamage == 0 && actualDamage == 0) {
            return;
        }
        var dfCard = this.getCard(defender);
        this.displayCardMsg({
            name: 'blockDamage',
            cardShape: dfCard.group,
            textColor: settings.blockDamageTextColor,
            text: '伤害: ' + originalDamage + '\r\n-->\r\n' + actualDamage,
        });
    };

    this.__protect = function(data) {
        var protector = data[0];
        //var attacker = data[1];
        var protectee = data[2];
        //var skill = data[3];
        var defendSkill = data[4];
        if (defendSkill == '格挡') {
            this.flyImage({ fileName: 'block.png', width: 48, height: 48 },
                    protector, [ protectee ], settings.skillDuration);
        }
    };
    
    this.msgIgnoredSkills = [
        '背刺', '暴击', '狂热', '嗜血', '横扫', '穿刺', '回春', '吸血', '振奋',
        '透支', '战意', '穷追猛打', '趁胜追击', '复仇', '奋战', '英雄杀手', '反噬',
        '圣光', '要害', '暗杀', '污染'
    ];
    this.selfUsedSkills = [
        '不动', '脱困', '群体脱困', '法力反射', '冰甲', '闪避', '守护', '魔神之甲', '灵巧',
        '王国之盾', '森林之盾', '蛮荒之盾', '地狱之盾', '弱点攻击', '无效', '圣盾', '不屈'
    ];
    this.__useSkill = function(data) {
        var attacker = data[0]; // EntityRuntimeInfo
        var skill = data[1];    // String
        var defenders = data[2]; // EntityRuntimeInfo[]
        if (defenders.length == 0) {
            return;
        }
        if (skill.indexOf('军团') == 0 ||
            skill.indexOf('原始') == 0 ||
            skill.indexOf('守护') == skill.length - 2 ||
            skill.indexOf('之力') == skill.length - 2 ||
            this.msgIgnoredSkills.indexOf(skill) >= 0) {
            return;
        }
        if (skill == '普通攻击') {
            this.normalAttack(attacker, defenders[0], false);
        } else if (this.selfUsedSkills.indexOf(skill) >= 0 || skill.indexOf('召唤') >= 0) {
            var skillText = skill;
            if (skill.indexOf('召唤') >= 0) {
                skillText = '召唤\r\n' + skill.substring(2);
            }
            var shape = this.getEntityShape(attacker);
            this.displayCardMsg({
                 name: skill,
                 cardShape: shape,
                 size: shape.getSize(),
                 text: skillText,
                 duration: settings.skillDuration,
             });
        } else if (skill == '吐槽') {
            this.flyImage({ fileName: 'tsukomi.png', width: 48, height: 48, text: skill },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '送还') {
            this.flyImage({ fileName: 'cross.png', width: 29, height: 60, text: skill },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '传送') {
            this.flyImage({ fileName: 'hexagram.png', width: 24, height: 24, text: skill, },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '免疫') {
            this.flyImage({ fileName: 'immue.png', width: 48, height: 48 },
                    attacker, [ attacker ], settings.skillDuration);
        } else if (skill == '魔甲') {
            this.flyImage({ fileName: 'magicshield.png', width: 48, height: 48 },
                    attacker, [ attacker ], settings.skillDuration);
        } else if (skill == '燃烧' || skill == '烈火焚神') {
            this.flyImage({ fileName: 'burn.png', width: 48, height: 48 },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '裂伤') {
            this.displayCardMsg({
                name: skill,
                cardShape: this.getEntityShape(defenders[0]),
                text: skill,
                duration: settings.skillDuration,
            });
        } else if (skill == '迷魂') {
            this.flyImage({ fileName: 'heart.png', width: 24, height: 24, text: skill },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '圣炎') {
            this.flyImage({ fileName: 'holyfire.png', width: 48, height: 48, text: skill },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '自爆') {
            this.flyImage({ fileName: 'explode.png', width: 48, height: 48, text: skill },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '虚弱' || skill == '战争怒吼') {
            this.flyImage({ fileName: 'soften.png', width: 48, height: 48, text: skill },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '疾病' || skill == '瘟疫') {
            this.flyImage({ fileName: 'disease.png', width: 48, height: 48, text: skill },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '削弱' || skill == '群体削弱') {
            this.flyImage({ fileName: 'weaken.png', width: 48, height: 48, text: skill },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '阻碍' || skill == '加速' || skill == '全体阻碍' || skill == '全体加速' || skill == '时光倒流') {
            this.flyImage({ fileName: 'delay.png', width: 48, height: 48, text: skill },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '法力侵蚀') {
            this.flyImage({ fileName: 'mana-drain.png', width: 48, height: 48, text: skill },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '净化') {
            this.flyImage({ fileName: 'purify.png', width: 48, height: 48, text: skill },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '血炼' || skill == '鲜血盛宴') {
            this.flyImage({ fileName: 'blood.png', width: 48, height: 48, text: skill },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '死亡印记') {
            this.flyImage({ fileName: 'death.png', width: 48, height: 48, text: skill },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '治疗' || skill == '甘霖' || skill == '回春' || skill == '治疗之雾') {
            this.flyImage({ fileName: 'heal.png', width: 24, height: 24, text: skill },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '冰弹' || skill == '霜冻新星' || skill == '暴风雪' || skill == '寒霜冲击') {
            this.flyImage({ fileName: 'ice.png', width: 24, height: 24, rotate: Math.PI * 4, text: skill },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '火球' || skill == '火墙' || skill == '烈焰风暴') {
            this.flyImage({ fileName: 'fire.png', width: 24, height: 24, text: skill },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '落雷' || skill == '连环闪电' || skill == '雷暴') {
            this.flyImage({ fileName: 'lightening.png', width: 24, height: 24, text: skill },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '毒液' || skill == '毒雾' || skill == '毒云') {
            this.flyImage({ fileName: 'poison.png', width: 24, height: 24, rotate: Math.PI * 4, text: skill },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '陷阱' || skill == '封印' || skill == '关小黑屋') {
            this.flyImage({ fileName: 'trap.png', width: 48, height: 48, text: skill },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '摧毁') {
            this.flyImage({ fileName: 'bomb.png', width: 48, height: 48, text: skill },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '盾刺' || skill == '反击') {
            this.flyImage({ fileName: 'spike.png', width: 48, height: 48, rotate: Math.PI * 4, text: skill },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '献祭') {
            this.flyImage({ fileName: 'round-cross.png', width: 48, height: 48, text: skill },
                    attacker, defenders, settings.skillDuration);
        } else if (skill == '狙击' || skill == '二重狙击' || skill == '魔神之刃') {
            this.flyImage({ fileName: 'aim.png', width: 48, height: 48, text: skill, },
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
            this.normalAttack(attacker, defenderHero, true);
        } else if (skill == '祈祷' || skill == '祈福') {
            this.flyImage({ fileName: 'heal.png', width: 24, height: 24, text: skill },
                    attacker, defenderHero, settings.skillDuration);
        } else if (skill == '诅咒' || skill == '魔神之咒') {
            this.flyImage({ fileName: 'skull.png', width: 30, height: 30, text: skill },
                    attacker, defenderHero, settings.skillDuration);
        } else if (skill == '自动扣血') {
            this.displayCardMsg({
                name: skill,
                cardShape: this.__getShape(defenderHero.id, 'hp-bar'),
                size: settings.getHeroHpBgRectSize(),
                text: skill,
                duration: settings.skillDuration,
            });
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
    
    this.__cardToOutField = function(data) {
        //var player = data[0];
        var cardRtInfo = data[1];
        this.showSplash({ text: cardRtInfo.uniqueName + '\r\n\r\n从墓地中被除外' });
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
        var text = '战斗结束!\r\n获胜者: ' + player.id + (data[3] > 0 ? ('\r\n共造成伤害: ' + data[3]) : '');
        this.showSplash({ text: text, exitType: 'onclick', });
    };
    
    this.__mapBattleResult = function(data) {
        this.addPause(2);
        this.showSplash({ text: data[0], exitType: 'onclick', });
    };
    
    /**
     * @param
     * card.ownerId,
     * card.uniqueName,
     * @return { x, y }
     */
    this.getCard = function(cardRtInfo, suppressErrorLog) {
        var card = this.arenas[cardRtInfo.ownerId].fields.ofName(cardRtInfo.uniqueName);
        if (card == null) {
            card = this.arenas[cardRtInfo.ownerId].hands.ofName(cardRtInfo.uniqueName);
        }
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

    this.compactHands = function(arena, _funcs) {
        var funcs = _funcs;
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
    
    this.getEntityShape = function(entityInfo) {
        var card = this.getCard(entityInfo, true);
        if (card) { return card.group; }
        var rune = this.getRune(entityInfo);
        return rune;
    };
    
    /**
     * @param img Object { fileName, width, height }
     * @param source The source entity (EntityRuntimeInfo) from which image flies
     * @param targets The target entities (EntityRuntimeInfo) or hero (PlayerRuntimeInfo) to which image flies
     */ 
    this.flyImage = function(imgObj, source, targetEntities, _duration) {
        var me = this;
        var duration = _duration;
        if (!duration) { duration = settings.flyImageDuration; }
        if (source.type != 'Card' && source.type != 'Rune') {
            console.error("Invalid source type: " + source.type);
            return;
        }
        var isCardSource = source.type == 'Card';
        var sourceShape = isCardSource ?
            this.getCard(source, true) :
            this.getRune(source, true);
        var isHeroTarget = $.type(targetEntities) != 'array';
        var targets = [];
        if (isHeroTarget) {
            targets.push(me.__getShape(targetEntities, 'hpbg-rect'));
        } else {
            $.each(targetEntities, function(i, c) {
                targets.push(me.getCard(c));
            });
        }
        var flyingFuncs = [];
        var destroyFuncs = [];
        $.each(targets, function(i, target) {
            var imgGroup = null;
            var tooltip = null;
            flyingFuncs.push(function() {
                var sourcePoint = { x: 0, y: 0 };
                var targetPoint = { x: 0, y: 0 };
                if (isHeroTarget) {
                    targetPoint.x = target.getX() + target.getWidth() / 2 - imgObj.width / 2;
                    targetPoint.y = target.getY() + target.getHeight() / 2 - imgObj.height / 2;
                } else {
                    targetPoint.x = target.group.getX() + target.width / 2 - imgObj.width / 2;
                    targetPoint.y = target.group.getY() + target.height / 2 - imgObj.height / 2;
                }
                
                if (sourceShape == null) {
                    sourcePoint.x = targetPoint.x;
                    sourcePoint.y = targetPoint.y + 30;
                } else {
                    if (isCardSource) {
                        sourcePoint.x = sourceShape.group.getX() + sourceShape.width / 2 - imgObj.width / 2;
                        sourcePoint.y = sourceShape.group.getY() + sourceShape.height / 2 - imgObj.height / 2;
                    } else {
                        sourcePoint.x = sourceShape.getX() - imgObj.width / 2;
                        sourcePoint.y = sourceShape.getY() - imgObj.height / 2;
                    }
                }
    
                var offsetX = 0;
                var offsetY = 0;
                var rotate = 0;
                if (imgObj.rotate) {
                    offsetX = imgObj.width / 2;
                    offsetY = imgObj.height / 2;
                    rotate = imgObj.rotate;
                }
                imgGroup = new Kinetic.Group({
                    x: sourcePoint.x + offsetX,
                    y: sourcePoint.y + offsetY,
                    offset: [offsetX, offsetY],
                });

                var imgSrc = new Image();
                imgSrc.src = resDir + '/img/effect/' + imgObj.fileName;
                imgSrc.onload = function() {
                    var img = new Kinetic.Image({
                        x: 0,
                        y: 0,
                        image: imgSrc,
                    });
                    imgGroup.add(img);
                };
                me.stage.get('#effect-layer')[0].add(imgGroup);

                if (imgObj.text) {
                    // tooltip
                    tooltip = new Kinetic.Label({
                        x: sourcePoint.x,
                        y: sourcePoint.y,
                        opacity: settings.skillTipOpacity,
                    });

                    tooltip.add(new Kinetic.Tag({
                        fill: settings.skillTipBgColor,
                        pointerDirection: 'down',
                        pointerWidth: settings.skillTipPointerWidth,
                        pointerHeight: settings.skillTipPointerHeight,
                        shadowColor: settings.skillTipShadowColor,
                        shadowBlur: settings.skillTipShadowBlur,
                        shadowOffset: settings.skillTipShadowOffset,
                        shadowOpacity: settings.skillTipShadowOpacity,
                    }));

                    tooltip.add(new Kinetic.Text({
                        text: imgObj.text,
                        fontFamily: settings.skillTipFontFamily,
                        fontSize: settings.skillTipFontSize,
                        padding: settings.skillTipPadding,
                        fill: settings.skillTipTextColor,
                    }));

                    me.stage.get('#effect-layer')[0].add(tooltip);
                }

                new Kinetic.Tween({
                    node: imgGroup,
                    x: targetPoint.x + offsetX,
                    y: targetPoint.y + offsetY,
                    duration: duration,
                    easing: imgObj.easing || Kinetic.Easings.StrongEaseOut, 
                }).play();
                new Kinetic.Tween({
                    node: imgGroup,
                    rotation: rotate,
                    duration: duration,
                }).play();
            });
            
            destroyFuncs.push(function() {
                imgGroup.destroy();
                delete imgGroup;
                if (tooltip != null) {
                    tooltip.destroy();
                    delete tooltip;
                }
            });
        });

        this.addAnimations("flyingImages(" + imgObj.fileName + ")", flyingFuncs, duration);
        this.addAnimations("destroyFlyingImages", destroyFuncs, settings.minimumDuration);
    };

    this.pieces = [];
    this.speeds = [ 0.25, 0.5, 1.0, 1.2, 1.5, 2.0, 3.0, 4.0, 5.0, 7.5, 10.0 ];
    this.speedIndex = 2;
    this.speed = 1.0 / this.speeds[this.speedIndex];

    this.enableAnimationLog = false;
    this.getSpeedText = function() {
        return this.speeds[this.speedIndex];
    };

    this.isSpeedIncreasible = function() {
        return this.speedIndex < this.speeds.length - 1;
    };
    
    this.isSpeedDecreasible = function() {
        return this.speedIndex > 0;
    };
    
    this.increaseSpeed = function() {
        if (!this.isSpeedIncreasible()) { return; }
        ++this.speedIndex;
        this.speed = 1.0 / this.speeds[this.speedIndex];
        this.pauseAnimations();
        this.playAnimations();
    };
    
    this.decreaseSpeed = function() {
        if (!this.isSpeedDecreasible()) { return; }
        --this.speedIndex;
        this.speed = 1.0 / this.speeds[this.speedIndex];
        this.pauseAnimations();
        this.playAnimations();
    };
    
    this.dumpAnimations = function() {
        var me = this;
        $.each(this.pieces, function(i, piece) {
            if (me.enableAnimationLog) {
                console.log("Piece[" + i + "] (" + piece.animation.duration + "): " + piece.animation.name);
            }
        });
    };
    
    this.clearAnimations = function() {
        this.pauseAnimations();
        this.pieces = [];
    };

    this.playAnimations = function() {
        var me = this;
        this.time = this.initTime;
        $.each(this.pieces, function(i, piece) {
            var animationName = piece.animation.name;
            var duration = piece.animation.duration;
            if (me.enableAnimationLog) {
                console.log("Scheduling animation '" + animationName + "' at " + me.time + " (Duration: " + duration + ")");
            }
            var timeOutVar = window.setTimeout(function() {
                if (me.enableAnimationLog) {
                    console.log("ANIM Executing " + animationName + " (duration = " + duration + ")");
                    console.log("ANIM Next animation should happen at " + new Date(new Date().getTime() + duration * 1000));
                }
                me.pieces.remove(piece);
                piece.animation.play(me.speed);
            }, me.time * 1000);
            piece.handle = timeOutVar;
            var timeSpent = me.speed * piece.animation.duration;
            if (timeSpent < settings.minimumDuration) {
                timeSpent = settings.minimumDuration;
            }
            var newTime = me.time + timeSpent;
            if (me.enableAnimationLog) {
                console.log("Time: " + me.time + " -> " + newTime);
            }
            me.time = newTime;
        });
    };
    
    this.stepTime = this.initTime;
    this.stepAnimation = function() {
        var piece = this.pieces.splice(0, 1)[0];
        var newTime = this.stepTime + this.speed * piece.animation.duration;
        if (this.enableAnimationLog) {
            console.log("Play animation '" + piece.animation.name + "'. Duration: " + piece.animation.duration +
                ". Time: " + this.stepTime + " -> " + newTime);
        }
        piece.animation.play(this.speed);
        this.stepTime = newTime;
    };
    
    this.pauseAnimations = function() {
        $.each(this.pieces, function(i, piece) {
            clearTimeout(piece.handle);
            delete piece.handle;
        });
    };

    this.__addAnimation = function(animation) {
        animation.name += '-' + this.pieces.length;
        this.pieces.push({ animation: animation });
    };
    
    this.addAnimation = function(name, func, duration) {
        this.__addAnimation(new Animation(name, func, duration));
    };
    
    this.addAnimations = function(name, funcs, duration) {
        this.__addAnimation(new Animation(name, funcs, duration));
    };
    
    this.addPause = function(duration) {
        this.__addAnimation(Animation.pause(duration));
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
        this.addAnimation("showSplash", function(speed) {
            splashRect.setX(-width);
            splashText.setText(text);
            splashText.centerMiddle(splashRect);
            new Kinetic.Tween({
                node: splashRect,
                x: 0,
                duration: duration * speed,
            }).play();
            new Kinetic.Tween({
                node: splashText,
                x: width / 2 - splashText.getWidth() / 2,
                duration: duration * speed,
            }).play();
        }, duration);
        if (exitType == 'pause') {
            this.addPause(pause);
            this.addAnimation("exitSplash", function(speed) {
                new Kinetic.Tween({
                    node: splashRect,
                    x: width,
                    duration: duration * speed,
                }).play();
                new Kinetic.Tween({
                    node: splashText,
                    x: width * 1.5 - splashText.getWidth() / 2,
                    duration: duration * speed,
                }).play();
            }, duration);
        }
    };
    
    /**
     * @param adjName: AT or HP
     * @param data: the event data
     * @param getEffect (boolean): whether is to add or lost effect
     */
    this.adjustValue = function(data, adjName, getEffect) {
        var source = data[0]; // EntityRuntimeInfo
        var target = data[1]; // EntityRuntimeInfo
        var adjustment = data[2]; // int
        var newValue = data[3]; // int
        var skillName = data[4]; // String
        var newMaxValue = data[5]; // int
        if (adjustment == 0) {
            return;
        }
        
        var ptSize = settings.getPortraitSize();
        var adjTextPrefix = getEffect ? '' : '失去\r\n';
        var adjValueText = adjustment > 0 ? '加' + adjustment : '减' + (-adjustment);
        var adjRect = null;
        var adjText = null;
        var adjGroup = null;
        var adjTextColor = null;
        var adjAT = adjName == 'AT';
        if (getEffect && adjAT && adjustment > 0) {
            adjTextColor = settings.addAtColor;
        } else if (getEffect && adjAT && adjustment < 0) {
            adjTextColor = settings.reduceAtColor;
        } else if (getEffect && !adjAT && adjustment > 0) {
            adjTextColor = settings.addHpColor;
        } else if (getEffect && !adjAT && adjustment < 0) {
            adjTextColor = settings.reduceHpColor;
        } else if (!getEffect && adjAT) {
            adjTextColor = settings.loseAdjAtColor;
        } else if (!getEffect && !adjAT) {
            adjTextColor = settings.loseAdjHpColor;
        }
        var me = this;
        this.addAnimation("adjustValuePrepare", function(speed) {
            adjRect = new Kinetic.Rect({
                x: 0, y: 0, width: ptSize.width, height: settings.adjRectHeight,
                stroke: settings.adjRectBorderColor,
                strokeWidth: settings.adjRectBorderWidth,
                fill: settings.adjRectFill,
                opacity: settings.adjRectOpacity,
            });
            adjText = new Kinetic.Text({
                x: 0, y: 0,
                text: adjTextPrefix + adjName + adjValueText + '\r\n' + skillName,
                fontFamily: settings.adjustFontFamily,
                fontSize: settings.adjustFontSize,
                fill: adjTextColor,
            });
            adjGroup = new Kinetic.Group({
                x: settings.width, y: settings.height,
            });
            adjGroup.add(adjRect).add(adjText);
            adjText.centerMiddle(adjRect);
            me.stage.get('#effect-layer')[0].add(adjGroup);
        }, settings.minimumDuration);
        var targetCard = this.getCard(target);
        if (source.type != 'Card' || source.uniqueName == target.uniqueName) {
            this.addAnimation("showAdjust" + adjName, function(speed) {
                adjGroup.setX(targetCard.group.getX());
                adjGroup.setY(-adjRect.getHeight());
                new Kinetic.Tween({
                    node: adjGroup,
                    x: adjGroup.getX(),
                    y: targetCard.group.getY() + ptSize.height / 2 - adjRect.getHeight() / 2,
                    duration: settings.adjustDuration / 5 * speed,
                    easing: Kinetic.Easings.StrongEaseOut,
                }).play();
            }, settings.adjustDuration);
        } else {
            var sourceCard = this.getCard(source, true);
            this.addAnimation("showAdjust" + adjName, function(speed) {
                if (sourceCard == null) {
                    adjGroup.setX(targetCard.group.getX());
                    adjGroup.setY(targetCard.group.getY());
                } else {
                    adjGroup.setX(sourceCard.group.getX());
                    adjGroup.setY(sourceCard.group.getY() + ptSize.height / 2 - adjRect.getHeight() / 2);
                }
                new Kinetic.Tween({
                    node: adjGroup,
                    x: targetCard.group.getX(),
                    y: targetCard.group.getY() + ptSize.height / 2 - adjRect.getHeight() / 2,
                    duration: settings.adjustDuration * speed,
                    easing: Kinetic.Easings.StrongEaseOut,
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
            var textShape = adjAT ? targetCard.atText : targetCard.hpText;
            var rectShape = adjAT ? targetCard.atRect : targetCard.hpRect;
            textShape.setText(adjName + ': ' + newValue);
            textShape.centerMiddle(rectShape);
            textShape.getLayer().draw();
        }, settings.minimumDuration);
        
        if (adjName == 'HP') {
            this.updateCardHP(targetCard, newValue, newMaxValue, settings.minimumDuration);
        }
    };
    
    this.updateCardHP = function(card, hp, maxHP, _duration) {
        var duration = _duration;
        if (!duration) { duration = settings.updateHpDuration; }
        var waitDuration = duration / 3;
        if (waitDuration < settings.minimumDuration) {
            waitDuration = settings.minimumDuration;
        }
        this.addAnimation('updateCardHp', function (speed) {
            card.hpText.setText('HP: ' + hp);
            card.hpText.centerMiddle(card.hpRect);
            new Kinetic.Tween({
                node: card.hpBar,
                width: hp * card.hpRect.getWidth() / maxHP,
                duration: duration * speed,
            }).play();
        }, waitDuration);
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
        var me = this;
        var opt = $.extend({
            textColor: settings.cardMsgTextColor,
            text: '',
            duration: settings.cardMsgDuration,
            size: settings.getPortraitSize(),
        }, (attrs || {}));
        var group = null;
        var rectHeight = settings.cardMsgRectHeight;
        if (rectHeight > opt.size.height) {
            rectHeight = opt.size.height;
        }
        this.addAnimation(attrs.name, function () {
            var rect = new Kinetic.Rect({
                x: 0, y: 0, width: opt.size.width,
                height: rectHeight,
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
                y: opt.cardShape.getY() + opt.size.height / 2 - rect.getHeight() / 2,
            });
            group.add(rect).add(text);
            me.stage.get('#effect-layer')[0].add(group);
            text.centerMiddle(rect);
            group.getLayer().draw();
        }, opt.duration);
        this.addAnimation(attrs.name + "Clearup", function() {
            if (group == null) { return; }
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
                { fileName: 'sword.png', width: 14, height: 44, },
                attacker,
                attackingHero ? defender : [ defender ]
        );
    };

    this.setup = function(data) {
        settings.refreshSize();
        this.clearAnimations();
        this.time = this.initTime;
        $('#battle-canvas').width(settings.width).height(settings.height);
        this.stage = new Kinetic.Stage({
            container : 'battle-canvas',
            width : settings.width,
            height : settings.height
        });
        var bgLayer = new Kinetic.Layer({ listening: false });
        var bg = new Kinetic.Rect({
            id: 'bg-layer',
            x : 0,
            y : 0,
            width : settings.width,
            height : settings.height,
            fill : settings.bgColor,
            strokeWidth : 0,
        });
        bgLayer.on('click', function(evt) { console.log('bg-layer ' + evt.targetNode.getId() + ' clicked'); });
        bgLayer.add(bg);
        this.stage.add(bgLayer);

        var events = data.events;
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

var refreshPlayStatus = function() {
    var playButton = $('#play-button');
    if (animater.playing) {
        playButton.changeButtonText('暂停');
        if (animater.isSpeedIncreasible()) {
            $('#faster-button').removeClass('ui-disabled');
        } else {
            $('#faster-button').addClass('ui-disabled');
        }
        if (animater.isSpeedDecreasible()) {
            $('#slower-button').removeClass('ui-disabled');
        } else {
            $('#slower-button').addClass('ui-disabled');
        }
    } else {
        playButton.changeButtonText('播放');
        $('#faster-button').addClass('ui-disabled');
        $('#slower-button').addClass('ui-disabled');
    }
    var text = '';
    if (animater.playing) {
        text += '播放中';
    } else {
        text += '暂停中';
    }
    text += '          播放速度：';
    text += animater.getSpeedText() + '速';
    $('#player-status').text(text);
};

BattleAnimation.togglePlayButton = function() {
    if (animater.playing) {
        animater.playing = false;
        animater.pauseAnimations();
    } else {
        animater.playing = true;
        animater.playAnimations();
    }
    refreshPlayStatus();
};

BattleAnimation.faster = function() {
    animater.increaseSpeed();
    refreshPlayStatus();
};

BattleAnimation.slower = function() {
    animater.decreaseSpeed();
    refreshPlayStatus();
};

BattleAnimation.showBattle = function(data) {
    animater.playing = true;
    $('#play-button').changeButtonText('暂停');
    refreshPlayStatus();
    animater.setup(data);
    animater.dumpAnimations();
    animater.playAnimations();
};

$(document)
.on("pageinit", "#arena", function(event) {
    animater = new Animater();
    $('#play-button').attr('href', 'javascript:CardFantasy.BattleAnimation.togglePlayButton();');
    $('#faster-button').attr('href', 'javascript:CardFantasy.BattleAnimation.faster();');
    $('#slower-button').attr('href', 'javascript:CardFantasy.BattleAnimation.slower();');
});

// END OF OUTERMOST IIFE
})(CardFantasy.BattleAnimation);