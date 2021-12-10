/**
 * reply
 */
console.log("reply module loaded");

var replyService = (function() {
	function add(reply,callback,error) {
		$.ajax({
			type : 'post',
			url : '/replies/new',
			data : JSON.stringify(reply),
			contentType : "application/json; charset=utf-8",
			success : function(result,status,xhr) {
				if(callback) {
					callback(result);
				}
			},
			error : function(xhr,status,err) {
				if(error) {
					error(err);
				}
			}
		});
	}
	
	function getList(param,callback,error) {
		var page = param.page || 1;
		$.ajax({
			type : 'get',
			url : '/replies/pages/' + param.bno + "/" + page + ".json",
			success : function(result,status,xhr) {
				if(callback) {
					callback(result.replyCnt,result.list);
				}
			},
			error : function(xhr,status,err) {
				if(error) {
					error(err);
				}
			}
		});
	}
	function get(rno,callback,error) {
		$.ajax({
			type : 'get',
			url : '/replies/' + rno + ".json",
			success : function(result,status,xhr) {
				if(callback) {
					callback(result);
				}
			},
			error : function(xhr,status,err) {
				if(error) {
					error(err);
				}
			}
		});
	}
	
	function remove(rno,callback,error) {
		$.ajax({
			type : 'delete',
			url : '/replies/'+ rno,
			success : function(result,status,xhr) {
				if(callback) {
					callback(result);
				}
			},
			error : function(xhr,status,err) {
				if(error) {
					error(err);
				}
			}
		});
	}
	function update(reply,callback,error) {
		$.ajax({
			type : 'put',
			url : '/replies/'+ reply.rno,
			data : JSON.stringify(reply),
			contentType : "application/json; charset=utf-8",
			success : function(result,status,xhr) {
				if(callback) {
					callback(result);
				}
			},
			error : function(xhr,status,err) {
				if(error) {
					error(err);
				}
			}
		});
	}
	function displayTime(timeValue) {
		var today = new Date();
		var gap = today.getTime() - timeValue;
		var dateObj = new Date(timeValue);
		var str = "";
		if(gap < (1000 * 60 * 60 * 24 )) {
			var hh = dateObj.getHours();
			var mm = dateObj.getMinutes();
			var ss = dateObj.getSeconds();
			
			return [(hh > 9 ? '' : '0') + hh,(mm > 9 ? '' : '0') + mm,(ss > 9 ? '' : '0') + ss].join(':');
		}// 1000ms -> 60s -> 60m -> 24h -> 1d
		else {
			var yy = dateObj.getFullYear();
			var mm = dateObj.getMonth() + 1; // zero base
			var dd = dateObj.getDate();
			
			return [(yy > 9 ? '' : '0') + yy,(mm > 9 ? '' : '0') + mm,(dd > 9 ? '' : '0') + dd].join('/');
		}
	}
	return {
		add : add, 
		getList : getList, 
		get : get,
		remove : remove,
		update : update,
		displayTime : displayTime
		};
})();
//var ret = (function{return ret})();  즉시 실행 함수 : 함수를 선언하고 즉시 실행함