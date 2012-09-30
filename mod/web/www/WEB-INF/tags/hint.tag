<%@ tag body-content="scriptless" %>
<%@ attribute name="fieldId" required="true" %>
<%@ attribute name="iconId" required="false" %>
<%@ attribute name="widthPx" required="true" %>

<div id="${fieldId}_hint" class="hint" style="width:${widthPx}px;"><span class="hint-pointer">&nbsp;</span><jsp:doBody/></div>
<script type="text/javascript">
    $(function() {
        var hint = $("#${fieldId}_hint");

        var showHint = function(elem) {
            var pos = elem.position();
            var posX = pos.left + elem.width() + 50;
            var posY = pos.top;
            hint.css({left: posX + 80, top: posY}).animate({left: posX, opacity: "show"}, 500);
        };

        $('#${fieldId}').focus(function() {
            showHint($(this));
        }).blur(function() {
            hint.hide();
        });
        if (${not empty iconId}) {
            $('#${iconId}').mouseover(function() {
                showHint($(this));
            }).mouseout(function() {
                hint.hide();
            });
        }
    });
</script>
