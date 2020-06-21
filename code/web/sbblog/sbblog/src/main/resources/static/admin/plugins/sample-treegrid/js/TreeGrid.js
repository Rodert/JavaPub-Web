var TreeGrid = function(_config){
  var _config = _config || {};	//设置
  var _defaultWidth = 150;	//默认宽度
  var _defaultAlign = "left";	//默认宽度
  var _idField = (_config.idField == '')||(_config.idField == null)?'id':_config.idField; //默认向后台传递的值name
  
  var _targetDiv = $("#" + _config.targetId);	//树table上层节点对象
  var _targetTable ;	//table对象

  var _headColumns = _config.headColumns || {};  //列设置信息
  var columnIndexCount = _headColumns.length - 1; //列数最大序列号，计数使用
  //初始化列
  initHeadColumns = function(){
	var head = '<thead>';
	head += '<tr class="head">';
	var tdStyle = '';
	var spanStyle = '';
	$.each(_headColumns, function(index, ele){
		//最后一个不赋宽度，默认填满空间
		if(index == columnIndexCount){
			head += '<td >';
		}else{
			if(index == 0){
				head += '<td ' + headStyle(ele, 'td') + '>';
			}else{
				head += '<td ' + headStyle(ele, 'both') + '>';
			}
		}
		
		head += '<div >';
		head += '<span >' + ele.title + '</span>';
		head += '</div>';
		head += '</td>';
	});
	head += '</tr>';
	head += '</thead><tbody></tbody>';
	_targetTable.append(head);	
  }
  //列样式style
  headStyle = function(column, type){
  	var style = 'style="';
  	var width = formatStr(column.width);
  	var align = formatStr(column.align);
  	var hiddenFlag = formatStr(column.hidden);

  	if(type == 'td'){
  		width = width == ''?_defaultWidth:width;
  		style += 'width:' + width + 'px;';
  	}else if(type == 'span'){
  		if(hiddenFlag == 'true'){
  			style += 'display:none;';
  		}
  	}else if(type == 'both'){
  		width = width == ''?_defaultWidth:width;
  		style += 'width:' + width + 'px;';
  		if(hiddenFlag == 'true'){
  			style += 'display:none;';
  		}
  	}
  	align = align == ''?_defaultAlign:align;
	style += 'text-align:' + align + ';';
  	style += '"';
  	//未处理的则清除style
  	if(style == 'style=""'){
  		style = '';
  	}
  	return style;
  }
  //处理空值
  formatStr = function(str){
  	return (str == null)||(str == '')?'':str;
  }
  
  	//追加添加数据, targetNode 为父节点，如果是table则在里面追加，如果是tr则在其后面添加
	addData = function(targetNode, data){
		var s = '';

		var allWidth = 0;
		var width = 0;
		var style = '';
		var textIndentStyle = '';
		var columnIndexCount = _headColumns.length - 1;	
		var field = '';
		var value = '';
		
		var targetType = $(targetNode).get(0).tagName;	//父节点元素类型，table  tr
		
		var targetId = $(targetNode).attr('id');	//父节点id
		var selfId = '';
		var offset = 20;	//图标偏移量
		var targetNodeOffset = (!$(targetNode).children('td:first').children('div').css('margin-left'))?0:$(targetNode).children('td:first').children('div').css('margin-left');
		targetNodeOffset = parseInt(targetNodeOffset);
		var divStyle = 'style="margin-left:' + (targetNodeOffset + offset) + 'px;"';	//偏移样式

		$.each(data, function(index, e){
		  selfId = '';
		  if(targetType == 'TABLE'){
			selfId = targetId + '_r_' + index;  
		  }else if(targetType == 'TR'){
			  selfId = targetId + '_' + index;
		  }
		  s += '<tr id="' + selfId + '" pid="' + targetId + '" trueId="' + e[_idField]   + '" class="closeNode" issynchronized="false">';		//初始情况下未同步过数据
		  for(var i = 0; i<=columnIndexCount; i++){
		  	field = _headColumns[i].field;
		  	value = formatValue(e, _headColumns[i]);//e[_headColumns[i].field];
			//第一列需要添加图标
			if(i == 0){
				s += '<td ' + headStyle(_headColumns[i], 'td') + '>';
				s += '<div class="tree-head" ' + divStyle + '>';

				s += '<span class="switch"></span>';
//				s += '<span class="ico"></span>';
				s += '<span field="' + field + '" '+ headStyle(_headColumns[i], 'span') + '>' + value + '</span>';

				s += '</div>';
				s += '</td>';
			}else{
				if(i == columnIndexCount){
					s += '<td >';
				}else{
					s += '<td '+ headStyle(_headColumns[i], 'both') + '>';
				}
				
				s += '<div>';
				
				s += '<span field="' + field + '" >' + value + '</span>';

				s += '</div>';
				s += '</td>';
			}		
		  }	  
		});
		
		if(targetType == 'TABLE'){
			$(targetNode).append(s); 
		}else if(targetType == 'TR'){
			$(targetNode).after(s); 
		}
		
		addSwitchEvent($(targetNode));
	}
	
	//格式化显示值，设定中有format的则根据设定修改值
	formatValue = function(ele, setting){
		var value = ele[setting.field] == null?"&nbsp;":ele[setting.field];	
		if(setting.format == null || setting.format == ''){
			return value;
		}else{
			if(typeof setting.format == 'function'){
				value = setting.format(ele, value);
			}else if(typeof setting.format == 'string'){
				value = eval('' + setting.format + "(ele,value)");
			}
		}
		return value
	}

	//同步数据
	synchroizeData = function(targetTr){
		var ifSynchronized = $(targetTr).attr("issynchronized");	
		if(ifSynchronized == 'true'){
			return false ;
		}else{
			var param = $(targetTr).find('span[field="id"]:first').html();
			var data = new Object();
			data[_idField] = param;
			$.ajax({
				url : _config.url + ";jsessionid=" + getSessionId(),
				dataType: 'json',
				data : data,
				success : function(data){
					var target = $(targetTr).parent();
					addData($(targetTr), data);
					$(targetTr).attr("issynchronized", true);
			}
		});
		}
		
	}
  
	//添加事件，clickNode是目标节点
	addSwitchEvent = function(targetNode){ 
		var targetNodeId = $(targetNode).attr('id');
		$('tr[pid="' + targetNodeId + '"]').find('.switch').parent().on('click', function(){	//对目标节点的子节点 span.switch添加事件
			var switchTr = $(this).parents('tr:first');	//相应tr
			var childrenNodes = $('tr[pid="' + $(switchTr).attr('id') + '"]');
			if($(switchTr).hasClass('closeNode')){
				$(switchTr).removeClass('closeNode');
				$(switchTr).addClass('openNode');
				synchroizeData($(switchTr));	//同步数据，目标节点为该节点的上层tr节点
				
				//去除子节点的隐藏属性
				$(childrenNodes).removeClass('hideLeaf');
			}else if($(switchTr).hasClass('openNode')){	//展开状态则转为收缩状态
				$(switchTr).removeClass('openNode');
				$(switchTr).addClass('closeNode');				
				
				childrenNodes.addClass('hideLeaf');
				
				closeNodes(switchTr);
				$.each(childrenNodes, function(index, ele){
					if(!$(ele).hasClass("closeNode")){
						$(ele).addClass("closeNode");
					}
				});
			
			}
		});
	}
	//关闭targetNode下所有关联节点，openNode-》closeNode， 添加hideLeaf
	closeNodes = function(targetNode){
		var targetNodeId = $(targetNode).attr('id');
		var childrenNode = $('tr[pid="' + targetNodeId + '"]');
		if(childrenNode.length > 0){
			$.each(childrenNode, function(index,ele){
				if($(ele).hasClass('openNode')){
					$(ele).removeClass('openNode');
					$(ele).addClass('closeNode');	
					
					closeNodes(ele);
				}
				if(!$(ele).hasClass('hideLeaf')){
					$(ele).addClass('hideLeaf');
				}
			});
		}else{
			return ;			
		}
	}
	
	//刷新targetNode下的数据：先删除该节点下的所有数据，级联删除，再从后台获取数据
	this.freshNodes = function(targetNode){
		var targetNodeType ;
		if(targetNode == null || targetNode.length == 0){
			targetNode = $(_targetTable);	//参数为空则视为刷新整株树
		}
		targetNodeType = $(targetNode).get(0).tagName;
		if(targetNodeType == 'TABLE'){	//如果刷新的是table，则直接清空tbody，再重新获取数据
			$(targetNode).find('tbody').empty();
			$(_targetTable).attr('issynchronized', 'false');
			synchroizeData($(_targetTable));
		}else{
			var targetNodeId = $(targetNode).attr('id');
			
			var childrenNode = $('tr[pid="' + targetNodeId + '"]');//
			if(childrenNode.length > 0){
				$.each(childrenNode, function(index, ele){
					deleteNode(ele);
				});
			}
			
			$(targetNode).attr('issynchronized', 'false');
			$(targetNode).removeClass('openNode');
			$(targetNode).removeClass('closeNode');
			$(targetNode).addClass('closeNode');
			$(targetNode).find('.switch').click();
		}
	}
	
	//this.freshNode = function(targetNode)
	
	//删除节点元素
	function deleteNode(targetNode){
		var targetNodeId = $(targetNode).attr('id');
		var childrenNode = $('tr[pid="' + targetNodeId + '"]');//
		if(childrenNode.length > 0){
			$.each(childrenNode, function(index, ele){
				deleteNode(ele);
			});
			
			$(targetNode).remove();
		}else{
			$(targetNode).remove();
		}
		
	}
  
  this.show = function(){
  	initTable();
	initHeadColumns();
	synchroizeData($(_targetTable));
	
	/*$(_targetTable).resizableColumns({
	    store: window.store
	});*/
  }
  
  
  this.getRootNode = function(){
	  return _targetTable;
  }

  function initTable(){
  	var tableId = _config.targetId + "-table";
  	var table = '<table id="' + tableId + '" class="tree table table-hover " >'
  	      + '</table>';
  	_targetDiv.append(table);

  	_targetTable = $("#" + tableId);
  }

}	