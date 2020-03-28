<#import  "parts/common.ftl" as cf>
<#import "parts/login.ftl" as l>
<@cf.page>

<div>
<@l.logout />
</div>

<form method="post">
    <input type="text" name="text" placeholder="Enter your message: "/>
    <input type="text" name="tag" placeholder="Tag"  />
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button type="submit">Add</button>
</form>

<div>
    Some text
</div>

<form method="post" action="filter">
    <input type="text" name="filter" placeholder="Filter:"/>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button type="submit">Find</button>
</form>

<div>List of messages:</div>
<#list messages as message>
    <div>
        <b>${message.id}</b>
        <span>${message.text}</span>
        <i>${message.tag}</i>
        <strong>${message.authorName}</strong>
    </div>
<#else>
No messages
</#list>
</@cf.page>