<#macro login path isRegisteredForm>

<form action="${path}" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">
            User Name:
        </label>
        <div class="col-sm-6">
            <input type="text" name="username" class="form-control"/>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">
            Password:
        </label>
        <div class="col-sm-6">
            <input type="password" name="password" class="form-control"/>
        </div>

    </div>
    <#if isRegisteredForm>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                Email:
            </label>
            <div class="col-sm-6">
                <input type="email" name="email" class="form-control" placeholder="some@some.com"/>
            </div>

        </div>
    </#if>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <#if !isRegisteredForm>
        <a href="/registration">Registration</a>
    </#if>
    <button type="submit" class="btn btn-primary">
        <#if isRegisteredForm>
            Register
        <#else>
            Sign In
        </#if>
    </button>
</form>

</#macro>

<#macro logout>
<form action="/logout" method="post">
    <button type="submit" class="btn btn-primary">Sign Out</button>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
</form>
</#macro>