var Jquery = require("jquery");
var fund = require("../html/fund.html");
var template = require('art-template');

// var remodal = require('remodal/dist/remodal.min.js');
//var remodal = require('imports?$=jquery!remodal/dist/remodal.min');
// var remodalcss1 = require('remodal/dist/remodal-default-theme.css');
// var remodalcss2 = require('remodal/dist/remodal.css');
var css = require("../assets/css/style.css");

// var img1 = require("../assets/image/kefu_40.png");
// var canyin7 = require("../assets/image/canyin7_40.png");
// var crm = require("../assets/image/crm_40.png");
// var yungyl = require("../assets/image/yungyl_40.png");
var moreSelected = require("../assets/image/more_40_selected.png");
var more = require("../assets/image/more_40.png");

var render = template.compile(fund);
var html = "";

Jquery.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}

template.helper("checkBind", function (value) {
    if (value.isBinded) {
        return value.color;
    } else {
        return "gray";
    }
});

template.helper("convertStr", function (value) {
    return String(value);
});

Jquery(function () {
    Jquery("body").append("<div id='floattargetcan'></div>");

    //console.log( require("../assets/image/kefu_40.png") );
    //console.log( Jquery( "#floattargetcan .floatFunProductCan" ).outerHeight() );

    function truefalseq(a, b) {
        if (a.isBinded && b.isBinded) {
            return 0;
        } else if (a.isBinded) {
            return -1
        } else if (b.isBinded) {
            return 1
        }
    }

    window.getProductData = function (queryParams, posturl) {
        var defaultUrl = "http://192.168.12.217:8080/manager";
        var managerUrl = defaultUrl + "/resources/index.html";
        var logoutUrl = defaultUrl + "/cors/logout";
        if (queryParams.url === undefined) {
            queryParams.url = defaultUrl + "/cors/getAllProducts?accountId=" + queryParams.id;
        }
        if (queryParams.id === undefined) {
            alert("请传入id参数");
            return;
        }
        if (queryParams.bottom) {
            Jquery("#floattargetcan").css("bottom", queryParams.bottom);
        }
        if (queryParams.right) {
            Jquery("#floattargetcan").css("right", queryParams.right);
        }

        var realparams = Jquery.extend({
            url: "data.json",
            datatype: "json",
            type: "get",
            success: function (data) {
                var parseD;

                if (typeof (data) == "object") {
                    parseD = data;
                    data.data.sort(truefalseq);
                    html = render(data);
                } else if (typeof (data) == "string") {
                    parseD = JSON.parse(data);
                    parseD.data.sort(truefalseq);
                    html = render(parseD);
                }


                Jquery("#floattargetcan").html(html);

                // var inst = Jquery('form[data-remodal-id=modal]').remodal({ hashTracking:false });
                var canyin7Loginurl;
                for (var i = 0; i < parseD.data.length; i++) {
                    if (parseD.data[i].productId === "001") {
                        canyin7Loginurl = parseD.data[i].loginUrl;
                    }
                }
                var currentClick;
                //产品按钮加事件
                Jquery(".floatFunProductCellIn").on("click", function () {
                    //填充input

                    currentClick = this;
                    var productId = Jquery(this).attr("productId");
                    var targetCan = Jquery("#remodal-input");
                    var isBinded = Jquery(this).attr("isBinded");
                    //console.log( isBinded );
                    if (isBinded === "false") {
                        Jquery(".remodal-inputcell").remove();
                        ;
                        for (var i = 0; i < parseD.data.length; i++) {
                            if (productId === parseD.data[i].productId) {
                                for (var p in parseD.data[i].parms) {
                                    var pause = Jquery("<div class='remodal-inputcell'></div>");
                                    pause.append(Jquery("<div class='remodal-left'>" + parseD.data[i].parms[p] + "：</div>"));
                                    pause.append(Jquery("<div class='remodal-right'><input name='" + p + "' type='text'></div>"));
                                    targetCan.append(pause);
                                }
                            }
                        }
                        //inst.open();
                    } else {
                        window.location.assign(Jquery(this).attr("loginUrl"));
                        // window.location.href = Jquery(this).attr( "loginUrl" );
                    }
                });

                //弹出层确认按钮事件
                //Jquery(document).on("confirmation",'.remodal',function(){
                // Jquery(".remodal-confirm").on("click",function(e){
                // 	// console.log( "confirm" );
                // 	//Jquery( ".remodal" ).attr( "action",posturl );
                // 	//Jquery(".remodal").submit();

                // 	//console.log( $(currentClick).attr( "productid" ) ); accountid
                // 	//console.log( $(".remodal").serializeObject() );
                // 	e.preventDefault();
                // 	var submitObj;
                // 	submitObj = $(".remodal").serializeObject();
                // 	submitObj.productId = $(currentClick).attr( "productid" );
                // 	submitObj.accountId = $(currentClick).attr( "accountid" );
                // 	Jquery.ajax({
                // 		url:posturl,
                // 		datatype:"json",
                // 		type:"post",
                // 		data:submitObj,
                // 		success:function( res ){
                // 			//console.log(res);
                // 			if( res.code === "0" ){
                // 				$( currentClick ).attr("isbinded","true")
                // 				.attr( "loginurl",res.data.loginUrl )
                // 				.css( "background",res.data.color );
                // 				inst.close();
                // 			}else{
                // 				alert( res.msg );
                // 			}
                // 		}
                // 	});

                // });

                //注销按钮加事件
                Jquery(".canyin7operate .cy7logout").on("click", function () {
                    //console.log( "点击了注销按钮" );
                    Jquery.ajax({
                        url: logoutUrl,
                        datatype: "json",
                        type: "get",
                        data: "accountId=" + parseD.data[0].accountId,//每一个产品的account id 都是一样的，随便找一个数组元素取
                        success: function (res) {
                            //console.log(res);
                            if (res.code === "0") {
                                window.location.href = canyin7Loginurl;
                            }
                        },
                        error: function () {
                            alert("网络问题");
                        }
                    });
                });

                // Jquery( "#base64img" ).attr("src",img1 );

                Jquery("#floattargetcan .floatFunProductCan").css(
                    "top",
                    -Jquery("#floattargetcan .floatFunProductCan").outerHeight() - 20
                );

                Jquery(".floatFunService").on("click", function () {
                    if (Jquery(".floatFunProductCan").css("display") == "none") {
                        Jquery(".floatFunService img").attr("src", moreSelected);
                        Jquery(".floatFunProductCan").fadeIn(300);
                    } else if (Jquery(".floatFunProductCan").css("display") != "none") {
                        Jquery(".floatFunService img").attr("src", more);
                        Jquery(".floatFunProductCan").fadeOut(300);
                    }
                });

                //判断返回值的属性，是不是显示餐饮7的两个功能，管理通行证 和 注销 这两个
                // console.log( parseD );
                Jquery("#floattargetcan .canyin7operate").css("display", "block");
                if (parseD.iscanyin7login) {
                    Jquery("#floattargetcan .canyin7operate a").css("display", "none");
                } else {
                    Jquery("#floattargetcan .canyin7operate a").click(function () {
                        window.location.assign(managerUrl);
                    });
                    Jquery("#floattargetcan .canyin7operate span").css("display", "none");
                }

            }
        }, queryParams);

        Jquery.ajax(realparams);
    }


});
