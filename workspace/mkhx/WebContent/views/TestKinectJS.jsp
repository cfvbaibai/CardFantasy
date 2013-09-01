<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<title>Test Kinectic JS</title>
<script type="text/javascript">
	var showSize = function() {
	    $('#client-info').text('WIDTH=' + $(window).width() + ', HEIGHT=' + $(window).height());
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

        // tooltip
        var tooltip = new Kinetic.Label({
          x: 50,
          y: 50,
          opacity: 0.5
        });

        tooltip.add(new Kinetic.Tag({
          fill: '#CCCCCC',
          pointerDirection: 'down',
          pointerWidth: 10,
          pointerHeight: 10,
          lineJoin: 'round',
          shadowColor: 'black',
          shadowBlur: 10,
          shadowOffset: 10,
          shadowOpacity: 0.5
        }));
        
        tooltip.add(new Kinetic.Text({
          text: 'Tooltip pointing down',
          fontFamily: 'Calibri',
          fontSize: 18,
          padding: 5,
          fill: 'white'
        }));


        
        var rect = new Kinetic.Rect({
            x: 50,
            y: 50,
            width: 100,
            height: 100,
            fill: 'red',
        });
        
        layer.add(rect);
        layer.add(tooltip);
        /*
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
        */
        
        /*
        var logo = portrait(70, 70, '1102');
        //logo.setScaleX(1.5);
        //logo.setScaleY(0.7);
        layer.add(logo);
        */
        stage.add(layer);
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