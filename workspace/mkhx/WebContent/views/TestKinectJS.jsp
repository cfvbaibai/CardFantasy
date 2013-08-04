<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<title>Test Kinectic JS</title>
<script type="text/javascript">
	var showSize = function() {
	    $('#client-info').text('WIDTH=' + $(window).width() + ', HEIGHT=' + $(window).height());
	};
	
    var portrait = function(x, y, id) {
        var group = new Kinetic.Group({ x: x, y: y });
        var cardAvatar = new Image();
        cardAvatar.src = resDir + '/img/cardlogo/' + id + '.jpg';
        cardAvatar.onload = function() {
            var cardAvatarImage = new Kinetic.Image({
                x : 2,
                y : 2,
               // width: 30,
                //height: 50,
                image : cardAvatar,
            });
            group.add(cardAvatarImage);
            cardAvatarImage.getLayer().draw();
        };
        var cardFrame = new Image();
        cardFrame.src = resDir + '/img/frame/frame.png';
        cardFrame.onload = function() {
            var cardFrameImage = new Kinetic.Image({
                x : 0,
                y : 0,
                //width: 32,
                //height: 52,
                image : cardFrame,
            });
            group.add(cardFrameImage);
            cardFrameImage.getLayer().draw();
        };
        return group;
    };

    $(document).ready(function() {
        showSize();
        $('#controller').click(show);
    });
    
    var show = function() {
        var stage = new Kinetic.Stage({
            container : 'battle-canvas',
            width : 300,
            height : 300
        });

        var layer = new Kinetic.Layer();

        var rect1 = new Kinetic.Rect({
            x : 10,
            y : 10,
            width : 50,
            height : 50,
            fill : 'green',
            stroke : 'black',
        });
        
        var rect2 = new Kinetic.Rect({
            x : 20,
            y : 20,
            width : 50,
            height : 50,
            fill : 'red',
            stroke : 'black',
        });
        
        var group = new Kinetic.Group({
            x: 50,
            y: 50,
        });
        group.add(rect1).add(rect2);
        layer.add(group);
        
        var logo = portrait(70, 70, '1102');
        //logo.setScaleX(1.5);
        //logo.setScaleY(0.7);
        layer.add(logo);
        stage.add(layer);
        
        new Kinetic.Tween({
            node: logo,
            x: logo.getX() + 100,
            y: logo.getY() + 20,
            duration: 2,
        }).play();
    };
</script>
</head>
<body>
    <div data-role="collapsible" data-collapsed="false" data-content-theme="d" style="PADDING: 10px; MAX-WIDTH: 320px; WIDTH: 100%">
        <h3>Canvas</h3>
        <div id="battle-canvas" style="BORDER: 1px solid black; WIDTH: 300px; HEIGHT: 300px"></div>
        <div id="client-info"></div>
        <div>
            <a data-role="button" href="#" id="controller" data-mini="true">Test</a>
        </div>
    </div>
</body>
</html>