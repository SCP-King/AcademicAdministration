$(document).ready(function (){
    $("#closebtn").click(function (){
        $("#mySidenav").css("width","0px")
    })
    $("#openbtn").click(function (){
        $("#mySidenav").css("width","250px")
    })
    $("#a1").click(function (){
        $("#content").attr("src","/student/toChangePwd")
    })
    $("#a2").click(function (){
        $("#content").attr("src","/student/detail")
    })
    $("#a3").click(function (){
        $("#content").attr("src","/student/browseCourse")
    })
    $("#a4").click(function (){
        $("#content").attr("src","/student/myCourse")
    })
    $("#a5").click(function (){
        location.replace("/")
    })












})