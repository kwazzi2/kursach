<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Сравнение</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style><%@include file="../../resources/css/style.css"%></style>
</head>
<body>
<%@include file="./elements/header.jsp"%>
<div class="mainContainer justify-content-center px-3 py-5" style="width: 70%">
    <div class="card chart-container">
        <canvas id="chart"></canvas>
    </div>
</div>
<script
        src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.js">
</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script>
    var xyValues = [
        {x:0.1, y:7},
        {x:1.3, y:8},
        {x:1.5, y:8},
        {x:1.7, y:9},
        {x:1.8, y:9},
        {x:2.1, y:9},
        {x:2.2, y:10},
        {x:2.3, y:11},
        {x:2.4, y:14},
        {x:2.5, y:14},
        {x:2.6, y:15},
        {x:2.7, y:8},
        {x:2.9, y:8},
        {x:3.3, y:9},
        {x:3.6, y:9},
        {x:3.8, y:9},
        {x:4.0, y:10},
        {x:4.1, y:11},
        {x:4.6, y:14},
        {x:4.8, y:14}
    ];
    console.log("script");
    $.ajax({
        type: "GET",
        url: "/chart/values", // Map your servlet here.
        data: {}
    }).done(function(data) {
        drawChart(data);
    });
    function drawChart(values) {
        const ctx = document.getElementById("chart").getContext('2d');
        const myChart = new Chart(ctx, {
            type: "scatter",
            data: {
                datasets: [{
                    pointRadius: 4,
                    pointBackgroundColor: "rgb(68,65,65)",
                    data: values
                }]
            },
            options: {
                legend: {display: false},
                scales: {
                    xAxes: [{ticks: {min: 0.0, max:5.0}}]
                }
            }
        });
    }
</script>
</body>
</html>