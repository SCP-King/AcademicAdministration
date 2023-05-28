$(document).ready(function (){
    $("#myForm1").bind("submit",function (event){
        event.preventDefault()
        let data=new FormData()
        data.append("answerImg",$("#answerImg1")[0].files[0])
        $.ajax({
            url:"/student/addAnswer",
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
    $("#myForm2").bind("submit",function (event){
        event.preventDefault()
        let data=new FormData()
        data.append("answerImg",$("#answerImg2")[0].files[0])
        $.ajax({
            url:"/student/updateAnswer",
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
})