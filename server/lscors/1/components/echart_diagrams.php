<?php
function echart_start(){
    echo "
    <script type=\"text/javascript\">
        // 路径配置
        require.config({
            paths:{ 
                'echarts' : 'js/echarts',
                'echarts/chart/pie' : 'js/echarts',
                'echarts/chart/bar' : 'js/echarts',
                'echarts/chart/line' : 'js/echarts'
            }
        });";
}

function diagram_bar(
		$dom, $title, $subtext,
		$category_name, $categories, 
		$value_name, $values){
	echart_start();
	echo "
	require(
		['echarts','echarts/chart/bar'],
		function(ec){
			var myChart = ec.init(document.getElementById('".$dom."'));
			option = {
                title:{
                    text:'".$title."',
                    subtext:'".$subtext."'
                },
				tooltip:{
					show: true
				},
				legend:{
					data:['".$title."']
				},
                toolbox:{
                    show:true,
                    feature:{
                        mark:{show:true},
                        dataView:{show:true,readOnly:false},
                        magicType:{show:true,type:['line','bar']},
                        restore:{show:true},
                        saveAsImage:{show:true}
                    }
                },
                calculable:true,
				xAxis:[{
					type: 'category',
					data: [".$categories."]
				}],
				yAxis:[{
					type: 'value'
				}],
				series:[{
					name: '".$title."',
					type: 'bar',
					data: [".$values."]
				}]
			};
			myChart.setOption(option);
		}
	);
    </script>";
}

function diagram_horizontal_bar(
        $dom, $title, $subtext,
        $category_name, $categories, 
        $value_name, $values){
    echart_start();
    echo "
    require(
        ['echarts','echarts/chart/bar'],
        function(ec){
            var myChart = ec.init(document.getElementById('".$dom."'));
            option = {
                title:{
                    text:'".$title."',
                    subtext:'".$subtext."'
                },
                tooltip:{
                    show: true
                },
                legend:{
                    data:['".$title."']
                },
                toolbox:{
                    show:true,
                    feature:{
                        mark:{show:true},
                        dataView:{show:true,readOnly:false},
                        magicType:{show:true,type:['line','bar']},
                        restore:{show:true},
                        saveAsImage:{show:true}
                    }
                },
                calculable:true,
                yAxis:[{
                    type: 'category',
                    data: [".$categories."],
                    name: '".$category_name."'
                }],
                xAxis:[{
                    type: 'value',
                    name: '".$value_name."'
                }],
                series:[{
                    name: '".$title."',
                    type: 'bar',
                    data: [".$values."]
                }]
            };
            myChart.setOption(option);
        }
    );
    </script>";
}

function diagram_pie(
		$dom, $title, $subtitle,
		$categories, $values){
    echart_start();
	echo "        
        require(
            [
                'echarts',
                'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('".$dom."')); 
                
                option = {
            	    title : {
            	        text: '".$title."',
            	        subtext: '".$subtitle."',
            	        x:'center'
            	    },
            	    tooltip : {
            	        trigger: 'item',
            	        formatter:\"{a} <br/>{b} : {c} ({d}%)\"
            	    },
            	    legend: {
            	        orient : 'vertical',
            	        x : 'left',
            	        data:[".$categories."]
            	    },
            	    toolbox: {
            	        show : true,
            	        feature : {
            	            mark : {show: true},
            	            dataView : {show: true, readOnly: false},
            	            restore : {show: true},
            	            saveAsImage : {show: true}
            	        }
            	    },
            	    calculable : true,
            	    series : [
            	        {
            	            name:'".$title."',
            	            type:'pie',
            	            radius : '55%',
            	            center: ['50%', '60%'],
            	            data:[".$values."]
            	        }
            	    ]
            	};
        
                // 为echarts对象加载数据 
                myChart.setOption(option); 
            }
        );
    </script>";
}

function diagram_line(
        $dom, $title, $subtitle,
        $category_name, $categories, 
        $value_name, $values){
    echart_start();
    echo "        
        require(
            [
                'echarts',
                'echarts/chart/line'
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('".$dom."')); 
                
                option = {
                    title:{
                        text:'".$title."',
                        subtext:'".$subtitle."',
                        x:'left'
                    },
                    tooltip:{
                        trigger:'axis'
                    },
                    legend:{
                        data:['".$title."']
                    },
                    toolbox:{
                        show:true,
                        feature:{
                            mark:{show:true},
                            dataView:{show:true,readOnly:false},
                            magicType:{show:true,type:['line','bar']},
                            restore:{show:true},
                            saveAsImage:{show:true}
                        }
                    },
                    calculable:true,
                    xAxis:[{
                        type:'category',
                        data:[".$categories."],
                        name:'".$category_name."'
                    }],
                    yAxis:[{
                        type:'value',
                        name:'".$value_name."'
                    }],
                    series:[{
                        name:'".$title."',
                        type:'line',
                        data:[".$values."]
                    }]
                };

                // 为echarts对象加载数据 
                myChart.setOption(option); 
            }
        );
    </script>";
}

?>