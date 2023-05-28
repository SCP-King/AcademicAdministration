$(document).ready(function (){
    $("#addCourse").click(function (){
        $("#popup").css("display","block")
        $("#overlay").css("display","block")
    })
    $("#closebtn").click(function (){
        $("#popup").css("display","none")
        $("#overlay").css("display","none")
    })
    $("#myForm").bind("submit",function (event){
        event.preventDefault()
        $.ajax({
            url:"/manger/addCourse",
            method:"post",
            data:$("#myForm").serialize(),
            success:function (res){
                alert(res)
                location.reload()
            },
            error:function (){
                alert("出错了!")
            }
        })
    })
    $("#find").click(function (){
        location.replace("/manger/browseCourse?condition="+$("#condition").val()+"&teaCondition="+$("#teaCondition").val())
    })
    $("#stufind").click(function (){
        location.replace("/student/browseCourse?condition="+$("#condition").val()+"&teaCondition="+$("#teaCondition").val())
    })










})