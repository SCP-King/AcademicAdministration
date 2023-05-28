$(document).ready(function (){
    $("#closebtn").click(function (){
        $("#mySidenav").css("width","0px")
    })
    $("#openbtn").click(function (){
        $("#mySidenav").css("width","250px")
    })
    $("#a2").click(function (){
        $("#content").attr("src","/manger/browseStudent")
    })
    $("#a3").click(function (){
        $("#content").attr("src","/manger/browseTeacher")
    })
    $("#a4").click(function (){
        $("#content").attr("src","/manger/browseCourse")
    })
    $("#a5").click(function (){
        location.replace("/")
    })













})