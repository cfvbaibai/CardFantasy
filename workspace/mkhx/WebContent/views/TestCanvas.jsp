<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<title>Test Canvas</title>
<script type="text/javascript">
var context = null;
var canvas = null;
window.requestAnimFrame = (function(callback) {
    return window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame
            || window.oRequestAnimationFrame || window.msRequestAnimationFrame || function(callback) {
                window.setTimeout(callback, 10);
            };
})();

var Animater = function(startNow) {
    this.startTime = startNow ? (new Date()).getTime() : null;
    this.start = function() {
        this.startTime = (new Date()).getTime();
        animate(this);
    };
    this.getElapsed = function() {
        if (this.startTime == null) {
            this.start();
        }
        return (new Date()).getTime() - this.startTime;
    };
    this.isRunning = function() {
        return this.startTime != null;
    };
    this.stop = function() {
        this.startTime = null;
    };
};

var amplitude = 1;
var period = 4000;

function animate(animater) {
    // update
    if (!animater.isRunning()) {
        return;
    }

    // clear
    context.clearRect(-150, -150, canvas.width, canvas.height);

    // draw stuff
    context.save();
    var t = animater.getElapsed();
    var r = amplitude * Math.sin(t * 2 * Math.PI / period);
    context.rotate(r);
    drawBlockFlower();
    context.restore();

    // request new frame
    window.requestAnimFrame(function() {
        animate(animater);
    });
}

var initCanvas = function() {
    $('#A')[0].innerText = amplitude;
    $('#T')[0].innerText = period;
    canvas = $('#battle-canvas')[0];
    context = canvas.getContext('2d');
    context.move = function(x, y) {
        this.moveTo(x + 0.5, y + 0.5);
    };
    context.line = function(x, y) {
        this.lineTo(x + 0.5, y + 0.5);
    };
    context.translate(150, 150);
    var animater = new Animater(true);
    $('#controller')[0].animater = animater;
    animater.start();
};

var drawLines = function() {
    context.beginPath();
    context.move(50, 100);
    context.line(150, 100);
    context.move(50, 102);
    context.line(150, 102);
    context.lineWidth = 1;
    context.stroke();
};

var drawBlock = function() {
    context.fillStyle = 'black';
    context.fillRect(-10, 0, 20, 100);
};

var drawBlockFlower = function() {
    for ( var i = 0; i < 12; ++i) {
        drawBlock();
        context.rotate(Math.PI * 2 / 12);
    }
};

var initController = function() {
    var controller = $('#controller');
    refreshController();
    controller.click(function () {
        var animater = controller[0].animater;
        if (animater.isRunning()) {
            animater.stop();
        } else {
            animater.start();
        }
        refreshController();
    });
};

var refreshController = function() {
    var animater = $('#controller')[0].animater;
    if (animater.isRunning()) {
        $('#controller .ui-btn-text').text('STOP');
    } else {
        $('#controller .ui-btn-text').text('START');
    }
};

$(document).ready(function() {
    initCanvas();
    initController();
});
</script>
</head>
<body>
    <div data-role="collapsible" data-collapsed="false" style="PADDING: 10px; MAX-WIDTH: 320px; WIDTH: 100%">
        <h3>Canvas</h3>
        <div>
            A=<span id="A"></span>, T=<span id="T"></span>
        </div>
        <div>
            <canvas id="battle-canvas" width="300" height="300" style="BORDER: 1px solid black"></canvas>
        </div>
        <div>
            <a id="battle-canvas-link">&nbsp;</a>
        </div>
        <div>
            <a data-role="button" href="#" id="controller" data-mini="true"></a>
        </div>
    </div>
</body>
</html>