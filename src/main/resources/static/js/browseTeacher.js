$(document).ready(function (){
    $("#find").click(function (){
        location.replace("/manger/browseTeacher?condition="+$("#condition").val())
    })
})