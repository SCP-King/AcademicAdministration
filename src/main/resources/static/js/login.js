$(document).ready(function () {
    let sub;
    $("#sub1").click(function (){
        sub="登录"
    })
    $("#sub2").click(function (){
        sub="注册"
    })
    $("#myForm").bind("submit", function (event) {
        if(sub!="注册" || $("#kind").val()=="manger") {
            event.preventDefault()
            let data = $("#myForm").serialize()
            data = data + "&sub=" + sub
            $.ajax({
                url: "/login",
                method: "post",
                data: data,
                success: function (res) {
                    alert(res)
                    if (res === "登录成功") {
                        if ($("#kind").val() === "student") {
                            location.replace("/student/function")
                        } else if ($("#kind").val() === "teacher") {
                            location.replace("/teacher/function")
                        } else {
                            location.replace("/manger/function")
                        }
                    }
                },
                error: function () {
                    alert('出错了!')
                },
            })
        }
    })

})