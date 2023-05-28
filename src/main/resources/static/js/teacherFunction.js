$(document).ready(function (){
    $("#closebtn").click(function (){
        $("#mySidenav").css("width","0px")
    })
    $("#openbtn").click(function (){
        $("#mySidenav").css("width","250px")
    })
    $("#a1").click(function (){
        $("#content").attr("src","/teacher/toChangePwd")
    })
    $("#a2").click(function (){
        $("#content").attr("src","/teacher/detail")
    })
    $("#a3").click(function (){
        $("#content").attr("src","/teacher/myCourse")
    })
    $("#a4").click(function (){
        location.replace("/")
    })













})