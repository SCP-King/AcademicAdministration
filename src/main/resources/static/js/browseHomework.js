$(document).ready(function (){
    $("#addVideo").click(function (){
        $("#popup").css("display","block")
        $("#overlay").css("display","block")
    })
    $("#closebtn").click(function (){
        $("#popup").css("display","none")
        $("#overlay").css("display","none")
    })
    $("#myForm").bind("submit",function (event){
        event.preventDefault()
        let data=new FormData()
        let f=$("#myForm").serializeArray()
        for (let i in f) {
            data.append(f[i].name,f[i].value)
            console.log(typeof (f[i].value))
        }
        data.append("workfile",$("#homework")[0].files[0])
        $.ajax({
            url:"/teacher/addHomework",
            method:"post",
            data:data,
            cache:false,
            contentType:false,
            processData:false,
            success:function (res){
                alert(res)
                location.reload()
            },
            error:function (){
                alert("出错了!")
            }
        })
    })
    $("#homeworkLab1").click(function (){
        location.replace("/student/browseHomework?kind="+$("input[type=radio]:checked").val())
    })
    $("#homeworkLab2").click(function (){
        location.replace("/teacher/browseHomework?kind="+$("input[type=radio]:checked").val())
    })





})