var mouseFrom = {},
    mouseTo = {},
    drawType = null,
    canvasObjectIndex = 0,
    textbox = null;
var drawWidth = 2; //笔触宽度
var color = "#E34F51"; //画笔颜色
var drawingObject = null; //当前绘制对象
var moveCount = 1; //绘制移动计数器
var doDrawing = false; // 绘制状态
var respo_data = null;
window.onload = function () {
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    });
    //绑定工具栏事件
    toolsBandingEvent();
    //初始化画板
    var canvas = new fabric.Canvas("c");
    load(canvas);

    jQuery("#download-png").click(function () {
        download_png();
    });
    jQuery("#download-pdf").click(function () {
        download_pdf(canvas);
    });

    canvas.on('mouse:up', function (options) {
        console.log(options.e.clientX, options.e.clientY);
        switch (drawType) {
            case "move":
                console.log("move");
                break;
            case "line":
                console.log("line");
                canvas.add(new_line(options));
                break;
            case "text":
                console.log("text");
                canvas.add(new_note(options));
                break;
            case "remove":
                console.log("remove");
                canvas.remove(options.target);
                break;
            case "save":
                console.log("save");
                break;
            default:
                console.log("default");
                break;
        }
    });
    $("#save").click(function () {
        save(canvas);
    });
    setInterval(function () {
        // save(canvas);
    }, 5000);
};


function toolsBandingEvent() {
    //绑定工具事件
    jQuery("#toolsul")
        .find("li")
        .on("click", function () {
            //设置样式
            jQuery("#toolsul")
                .find("li>i")
                .each(function () {
                    jQuery(this).attr("class", jQuery(this).attr("data-default"));
                });
            jQuery(this)
                .addClass("active")
                .siblings()
                .removeClass("active");
            jQuery(this)
                .find("i")
                .attr(
                    "class",
                    jQuery(this)
                        .find("i")
                        .attr("class")
                        .replace("black", "select")
                );
            drawType = jQuery(this).attr("data-type");
        });
}

function new_note(options) {
    var note = new fabric.Textbox('please~ input...', {
        left: options.e.clientX - 100,
        top: options.e.clientY - 50,
        fontFamily: 'Comic Sans MS',
        fontSize: '24',
        fontWeight: "normal",
        fontStyle: "",
        backgroundColor: 'rgba(153,77,82,0.3)',
        borderColor: 'rgba(153,77,82,0.6)',
        hasControls: false, //选中时是否可以放大缩小
        hasRotatingPoint: false//选中时是否可以旋转
    });
    $("#move").click();
    return note;
}

function new_line(options) {
    var line = new fabric.Line([options.e.clientX, options.e.clientY, options.e.clientX + 600, options.e.clientY],
        {//终止位置，线长，起始位置，top，这里是从项目中截下来的我用了变量代替，你要用的话lineheight和lineleft用自己的变量或者数字代替。如果两个终止位置和起始位置的数值一样那么这个线条会垂直，这个应该很好理解。
            fill: 'rgba(0,0,0,0.9)',//填充颜色
            stroke: 'rgba(0,0,0,0.5)',//笔触颜色
            strokeWidth: 4,//笔触宽度
            hasControls: true, //选中时是否可以放大缩小
            hasRotatingPoint: true,//选中时是否可以旋转
            hasBorders: false,//选中时是否有边框
            transparentCorners: true,
            perPixelTargetFind: true,//默认false。当设置为true，对象的检测会以像互点为基础，而不是以边界的盒模型为基础。
            selectable: true//是否可被选中
        });
    $("#move").click();
    return line;
}

var saved_time = "";

function save(canvas) {
    let p = jQuery("#auto-save-p");
    p.text("saving");
    respo_data.data = JSON.stringify(canvas.toJSON());
    if (respo_data == null) {
        p.text("no data");
        return;
    }
    $.ajax({
        type: 'PUT',
        url: "/v1/usms/" + respo_data.id,
        contentType: "application/json",
        data: JSON.stringify({
            "name": respo_data.name,
            "description": respo_data.description,
            "data": respo_data.data,
            "status": respo_data.status
        }),
        success: function () {
            saved_time = new Date().getHours() + " : " + new Date().getSeconds();
            p.html("saved at</br>" + saved_time);
        },
        error: function () {
            p.html("saved at</br>" + saved_time);
        }
    });
}

function load(canvas) {
    var id = getQueryString("id");
    $.ajax({
        type: 'GET',
        async: true,
        url: "/v1/usms/" + id,
        success: function (data) {
            if (data.toString().startsWith('<!DOCTYPE html>')) {
                window.location.reload()
            }
            if (data.data.length > 0) {
                canvas.loadFromJSON(JSON.parse(data.data));
            }
            respo_data = data
        },
        error: function (data) {
            console.log(data)
        },
        complete: function (data) {
            console.log(data.status);
        }
    })
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function sleep(time) {
    return new Promise((resolve) => setTimeout(resolve, time));
}

function download_png() {

    domtoimage.toPng(document.getElementById('c'), {quality: 1})
        .then(function (dataUrl) {
            var link = document.createElement('a');
            link.download = 'download-UserStoryMapping.png';
            link.href = dataUrl;
            link.click();
        });
}

function download_pdf(canvas) {
    var contentWidth = canvas.width;
    var contentHeight = canvas.height;
    //一页pdf显示html页面生成的canvas高度;
    var pageHeight = contentWidth / 592.28 * 841.89;
    //未生成pdf的html页面高度
    var leftHeight = contentHeight;
    //页面偏移
    var position = 0;
    //a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高
    var imgWidth = 595.28;
    var imgHeight = 592.28/contentWidth * contentHeight;

    var imgData = canvas.toDataURL('image/jpeg', 1.0);
    var doc = new jsPDF('', 'pt', 'a4');
    doc.addImage(imgData, 'PNG', 0, 0, imgWidth, imgHeight);
    doc.save("download-UserStoryMapping.pdf");
}