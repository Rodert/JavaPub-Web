<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Lee-Blog</title>
    <!-- Bootstrap Core CSS -->
    <link href="/static/admin/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- 列表 -->
    <link href="/static/admin/plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet">
    <!-- 弹出对话框 -->
    <link href="/static/admin/plugins/bootstrap-dialog/css/bootstrap-dialog.css" rel="stylesheet">
    <!-- 消息提示 -->
    <link href="/static/admin/plugins/toastr/toastr.css" rel="stylesheet">
    <link href="/static/admin/plugins/pace/themes/pace-theme-minimal.css" rel="stylesheet">
    <link href="/static/admin/plugins/jquery-validate/css/validate.css" rel="stylesheet" type="text/css">
    <!-- 菜单 -->
    <link href="/static/admin/plugins/metisMenu/metisMenu.css" rel="stylesheet">
    <!-- 自定义样式 CSS -->
    <link href="/static/admin/css/main-style.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="/static/admin/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="skin-default ">
<div class="wrapper">
    <div class="main-header">
        <nav class="navbar nav-static-top">
            <a href="#" class="logo  hidden-sm hidden-xs"><i class="fa  fa-coffee"></i> <span>博客管理</span></a>
            <div class="navbar-custom-menu pull-left ">
                <ul class="nav navbar-nav">
                    <li><a href="javascript:void(0);" class="sidebar-toggle"> <i class="fa fa-bars"></i>
                        </a></li>
                </ul>
            </div>
            <div class="navbar-custom-menu pull-right">
                <ul class="nav navbar-nav">

                    <li class="dropdown notifications-menu "><a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-bell-o"></i> <span class="label label-warning">4</span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-right">
                            <li>
                                <p class="yellow">你有4条通知</p>
                            </li>
                            <li><a href="#"> <span class="label label-danger"> <i class="fa fa-bell-o"></i>
									</span> 服务于50分钟前重启。 <span class="pull-right text-muted small">34 分钟</span>
                                </a></li>
                            <li><a href="#"> <span class="label label-danger"> <i class="fa fa-bell-o"></i>
									</span> 服务于50分钟前重启。 <span class="pull-right text-muted small">34 分钟</span>
                                </a></li>
                            <li><a href="#"> <span class="label label-danger"> <i class="fa fa-bell-o"></i>
									</span> 服务于50分钟前重启。 <span class="pull-right text-muted small">34 分钟</span>
                                </a></li>
                            <li><a href="#"> <span class="label label-warning"> <i class="fa fa-plus"></i>
									</span> 服务于10分钟停机了。 <span class="pull-right text-muted small">23 分钟</span>
                                </a></li>
                            <li><a href="#" class="dropdown-footer">显示所有通知</a></li>
                        </ul>
                    </li>
                    <li class="dropdown user user-menu "><a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <img alt="" src="/static/admin/images/avator.jpg" class="user-image"> <span>
                                <#if Session["admin"]?exists>${Session["admin"].nickname}</#if><i class="fa fa-caret-down"></i>
							</span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-right">
                            <li><a href="/admin/logout"><i class="fa fa-power-off"></i>退出系统</a></li>
                        </ul>
                    </li>
                    <li><a href="/admin/logout" title="退出" data-placement="bottom" data-toggle="tooltip"> <i
                                    class="fa fa-power-off"></i>
                        </a></li>
                </ul>
            </div>
        </nav>
    </div>