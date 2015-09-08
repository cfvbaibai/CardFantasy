<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<title>Test Kinectic JS</title>
<script type="text/javascript">
	var showSize = function() {
	    $('#client-info').text('WIDTH=' + $(window).width() + ', HEIGHT=' + $(window).height());
	};

    $(document).ready(function() {
        showSize();
        $('#controller').click(show);
        showChart();
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
        
        rect.on('click', function() {
            console.log('rect is clicked');
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
    
    var showChart = function() {
        var data = {
            labels: ["January", "February", "March", "April", "May", "June", "July"],
            datasets: [
                {
                    label: "My First dataset",
                    fillColor: "rgba(220,220,220,0.2)",
                    strokeColor: "rgba(220,220,220,1)",
                    pointColor: "rgba(220,220,220,1)",
                    pointStrokeColor: "#fff",
                    pointHighlightFill: "#fff",
                    pointHighlightStroke: "rgba(220,220,220,1)",
                    data: [65, 59, 80, 81, 56, 55, 40]
                },
                {
                    label: "My Second dataset",
                    fillColor: "rgba(151,187,205,0.2)",
                    strokeColor: "rgba(151,187,205,1)",
                    pointColor: "rgba(151,187,205,1)",
                    pointStrokeColor: "#fff",
                    pointHighlightFill: "#fff",
                    pointHighlightStroke: "rgba(151,187,205,1)",
                    data: [28, 48, 40, 19, 86, 27, 90]
                }
            ]
        };
        var ctx = $("#test-chart")[0].getContext("2d");
        // This will get the first returned node in the jQuery collection.
        var chart = new Chart(ctx);
        chart.Line(data);
    };
</script>
</head>
<body>
    <div data-role="collapsible" data-collapsed="false"
        data-content-theme="d"
        style="PADDING: 10px; MAX-WIDTH: 320px; WIDTH: 100%">
        <h3>Canvas</h3>
        <div id="battle-canvas"
            style="BORDER: 1px solid black; WIDTH: 300px; HEIGHT: 300px"></div>
        <div id="client-info"></div>
        <div>
            <a data-role="button" href="#" id="controller"
                data-mini="true">Test</a>
        </div>
    </div>
    <div data-role="collapsible" data-collapsed="false"
        data-content-theme="d" style="WIDTH: 500px">
        <h3>卡组</h3>
        <div>
            <div id="card-filter">
                <table style="width: 100%">
                    <tr>
                        <td>筛选</td>
                        <td style="width: 40%"><select
                            data-mini="true">
                                <option>全部种族</option>
                                <option>王国</option>
                                <option>森林</option>
                                <option>蛮荒</option>
                                <option>地狱</option>
                        </select></td>
                        <td style="width: 40%"><select
                            data-mini="true">
                                <option>全部星数</option>
                                <option>一星</option>
                                <option>二星</option>
                                <option>三星</option>
                                <option>四星</option>
                                <option>五星</option>
                        </select></td>
                    </tr>
                </table>
            </div>
            <div>
                <ul data-role="listview" data-mini="true"
                    style="WIDTH: 100%">
                    <li data-mini="true" data-wrapperels="div">
                        <table style="WIDTH: 100%">
                            <tr>
                                <td><a href="#ppp1"
                                    data-rel="popup" data-mini="true">城镇弓箭手</a></td>
                                <td><select data-mini="true">
                                        <option>LV 0</option>
                                        <option>LV 1</option>
                                        <option>LV 2</option>
                                </select></td>
                                <td style="text-align: right">洗炼</td>
                                <td><select data-mini="true">
                                        <option>陷阱</option>
                                        <option>不动</option>
                                        <option>雷暴</option>
                                </select></td>
                                <td><select data-mini="true">
                                        <option>LV 0</option>
                                        <option>LV 1</option>
                                        <option>LV 2</option>
                                </select></td>
                                <td><a href="#" data-role="button"
                                    data-inline="true" data-mini="true"
                                    data-icon="plus"
                                    data-iconpos="notext"></a>
                            </tr>
                        </table>
                    </li>
                    <li><a href="#ppp2" data-rel="popup">金属巨龙</a></li>
                    <li><a href="#ppp3" data-rel="popup">降临天使</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div data-role="popup" id="ppp1">
        <a data-role="button" data-rel="popup" href="#ppp2">Open
            Another Popup</a>
    </div>
    <div data-role="popup" id="ppp2">
        <span>Great!</span>
    </div>
    <div data-role="popup" id="ppp3">
        <span>Oh!</span>
    </div>
    
    <canvas id="test-chart" width="400" height="200"></canvas>
</body>
</html>