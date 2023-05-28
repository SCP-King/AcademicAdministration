$(document).ready(function (){
    $("#find").click(function (){
        location.replace("/manger/browseStudent?condition="+$("#condition").val())
    })
})