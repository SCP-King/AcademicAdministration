$(document).ready(function () {
    $("#myForm").bind("submit", function (event) {
        event.preventDefault()
        if ($("#newpwd1").val() !== $("#newpwd2").val()) {
            alert("新密码和确认密码不一致")
            return;
        }
        $.ajax({
            url: "/manger/changePwd",
            method: "post",
            data: $("#myForm").serialize(),
            success: function (res) {
                alert(res)
            },
            error: function () {
                alert("出错了!")
            }
        })
    })
}
)
