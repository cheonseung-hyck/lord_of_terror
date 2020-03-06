<!DOCTYPE HTML>
<HTML>
    <HEAD>
        <?php
            $myArr=array(array());
            $connect = mysqli_connect("localhost","root","","mydatabase",3306);

            mysqli_query($connect,"CREATE TABLE TestTable (UID INT PRIMARY KEY, NAME VARCHAR(10), AGE INT, RANK VARCHAR(10))");

            mysqli_query($connect,"INSERT INTO TestTable (UID,NAME,AGE,RANK) VALUES (414, '천승혁', 25, '인턴') ");
            mysqli_query($connect,"INSERT INTO TestTable (UID,NAME,AGE,RANK) VALUES (415, '홍길동', 32, '대리') ");
            mysqli_query($connect,"INSERT INTO TestTable (UID,NAME,AGE,RANK) VALUES (416, '두울리', 34, '대리') ");
            mysqli_query($connect,"INSERT INTO TestTable (UID,NAME,AGE,RANK) VALUES (417, '고길동', 40, '부장') ");
            mysqli_query($connect,"INSERT INTO TestTable (UID,NAME,AGE,RANK) VALUES (418, '또오치', 35, '과장') ");
            mysqli_query($connect,"INSERT INTO TestTable (UID,NAME,AGE,RANK) VALUES (419, '마이콜', 38, '과장') ");

            $res = mysqli_query($connect,"SELECT RANK AS rank, COUNT(*) AS NUMBER FROM TestTable GROUP BY RANK");
            $idx=0;
            while($arr=mysqli_fetch_array($res)){
                if($idx>=5)break;
                array_push($myArr,array($arr['rank'],$arr['NUMBER']));

                $idx=$idx+1;
            }
        ?>
        <meta charset='utf-8'>
        <title>직급별 직원 수</title>
        <script>
            let canvas;
            let context;
            function initCanvas(){
                canvas = document.getElementById('myCanvas');
                canvas.style.backgroundColor="aliceblue";
                context = canvas.getContext("2d");
                context.textAlign="center";
                drawAllChart();
            }
            function drawChart(data){
                let x_offset = data.x_offset;
                let y_offset = data.canvas_height-(data.y_value/data.max_data*0.8*data.canvas_height);
                let x_length = data.x_width;
                let y_length = data.y_value/data.max_data*0.8*data.canvas_height;

                context.fillText(data.x_value,data.x_offset+x_length/2,data.y_offset);
                context.fillRect(x_offset,y_offset,x_length,y_length);
            }
            function drawAllChart(){
                let myArray = <?php echo json_encode($myArr); ?>;
                let maxData = myArray[1][1];
                for(i=1;i<myArray.length;i++){
                    if(maxData<myArray[i][1])maxData=myArray[i][1];
                }

                for(i=1;i<myArray.length;i++){
                    drawChart({
                        x_value: myArray[i][0],
                        y_value: myArray[i][1],
                        canvas_height: canvas.height,
                        x_width: canvas.width/myArray.length-100,
                        x_offset: (canvas.width/myArray.length-70)*i+10,
                        y_offset: 50,
                        max_data: maxData
                    });
                }
            }
        </script>
    </HEAD>
    <BODY onload="initCanvas()">
        <canvas id="myCanvas" width='800' height='500'></canvas>
    </BODY>
</HTML>