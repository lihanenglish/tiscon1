<#import "layout.ftl" as layout/>
<#import "spring.ftl" as spring/>

<@layout.layout "Register">
    <div id="all">
        <div id="content">
            <div class="container">
                <div class="col-md-12">
                    <ul class="breadcrumb">
                        <li><a href="/">Home</a></li>
                        <#if genreName?has_content>
                            <li><a href="/category?genreId=${genreId}&subgenreId=">${genreName} </a></li>
                        </#if>
                        <#if subgenreName?has_content>
                            <li>${subgenreName} </a></li>
                        </#if>
                    </ul>
                </div>

                <div class="col-md-3">
                    <!-- *** MENUS AND FILTERS ***
 _________________________________________________________ -->
                    <div class="panel panel-default sidebar-menu">

                        <div class="panel-heading">
                            <h3 class="panel-title">Ranking</h3>
                        </div>

                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked category-menu">
                                <#if genreId=="33">
                                    <#if subgenreId?has_content>
                                        <li>
                                    <#else>
                                        <li class="active">
                                    </#if>
                                <#else>
                                    <li>
                                </#if>
                                        <a href="/category?genreId=33&subgenreId=">MOVIE </a>
                                        <ul>
                                            <#list movieGenres as movie>
                                                <li>
                                                    <#if subgenreId==movie.id>
                                                        <div style="background:#4fbfa8;"><a href="/category?genreId=33&subgenreId=${movie.id}">${movie.name?html}</a></div>
                                                    <#else>
                                                        <a href="/category?genreId=33&subgenreId=${movie.id}">${movie.name?html}</a>
                                                    </#if>
                                                </li>
                                            </#list>
                                        </ul>
                                    </li>
                                <#if genreId=="34">
                                    <#if subgenreId?has_content>
                                        <li>
                                    <#else>
                                        <li class="active">
                                    </#if>
                                <#else>
                                    <li>
                                </#if>
                                        <a href="/category?genreId=34&subgenreId=">MUSIC </a>
                                        <ul>
                                            <#list musicGenres as music>
                                                <li>
                                                    <#if subgenreId==music.id>
                                                        <div style="background:#4fbfa8;"><a href="/category?genreId=34&subgenreId=${music.id}">${music.name?html}</a></div>
                                                    <#else>
                                                        <a href="/category?genreId=34&subgenreId=${music.id}">${music.name?html}</a>
                                                    </#if>
                                                </li>
                                            </#list>
                                        </ul>
                                    </li>
                            </ul>
                        </div>
                    </div>
                    <!-- *** 検索機能用エリア
                    <div class="panel panel-default sidebar-menu">
                        <div class="panel-heading">
                            <h3 class="panel-title">Search</h3>
                        </div>
                    </div>
                    *** -->
                    <!-- *** MENUS AND FILTERS END *** -->
                </div>

                <div class="col-md-9">
                    <div class="box">
                        <#if subgenreName?has_content>
                            <h1>${subgenreName} </h1>
                        <#else>
                            <h1>${genreName} </h1>
                        </#if>
                        <p>上位商品の数を選択してください。</p>
                         <form>
                           <input type="radio" name="No" value="10" /> 10
                            <br />
                            <input type="radio" name="No" value="20" /> 20
                         </form>
                    </div>
                   <!-- <#assign y = request.getParameterValues("No") /> -->
                    <div class="row products">

                        <#list ranking as itemtop10>
                        <div class="col-md-4 col-sm-6">
                            <div class="product">
                                <div class="flip-container">
                                    <div align="center">
                                        <p><h3>${itemtop10?counter}位</h3></p>
                                        <div>
                                            <div>
                                                <a href="/detail?genreId=${genreId}&subgenreId=${subgenreId}&itemId=${itemtop10.id}">
                                                    <img src=${itemtop10.image} alt=${itemtop10.title?html} class="img-responsive img-category-list">
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <a href="/detail?genreId=${genreId}&subgenreId=${subgenreId}&itemId=${itemtop10.id}" class="invisible">
                                    <img src=${itemtop10.image} alt=${itemtop10.title?html} class="img-responsive img-category-list">
                                </a>
                                <div class="text">
                                    <h3 class="text-category-list-title"><a href="/detail?genreId=${genreId}&subgenreId=${subgenreId}&itemId=${itemtop10.id}">${itemtop10.title}</a></h3>
                                    <p class="price">￥${itemtop10.price}</p>
                                    <p class="buttons">
                                        <a href="/detail?genreId=${genreId}&subgenreId=${subgenreId}&itemId=${itemtop10.id}" class="btn btn-default">View detail</a>
                                        <a href="basket.html" class="btn btn-primary"><i class="fa fa-shopping-cart"></i>Add to cart</a>
                                    </p>
                                </div>
                                <!-- /.text -->
                            </div>
                            <!-- /.product -->
                        </div>
                      </#list>
                    </div>
                    <!-- /.products -->
                </div>
                <!-- /.col-md-9 -->
            </div>
            <!-- /.container -->
        </div>
        <!-- /#content -->
    </div>
    <!-- /#all -->
</@layout.layout>
