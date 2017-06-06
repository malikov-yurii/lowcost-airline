<%@page contentType="text/html" pageEncoding="UTF-8" %>

<div class="dropdown select-lang">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown">${pageContext.response.locale}<b class="caret"></b></a>
    <ul class="dropdown-menu">
        <li><a onclick="show('en')">English</a></li>
        <li><a onclick="show('ua')">Українська</a></li>
    </ul>
</div>
<script type="text/javascript">
    var localeCode="${pageContext.response.locale}";
    function show(lang) {
        window.location.href = window.location.href.split('?')[0] + '?lang=' + lang;
    }
</script>