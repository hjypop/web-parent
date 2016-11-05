new function() {
	var _self = this;
	_self.width = 640;
	_self.fontSize = 100;
	_self.widthProportion = function() {
		var p = (document.body && document.body.clientWidth || document
				.getElementsByTagName("html")[0].offsetWidth)
				/ _self.width;
		return p > 1 ? 1 : p < 0.5 ? 0.5 : p;
	};
	_self.changePage = function() {
		document.getElementsByTagName("html")[0].setAttribute("style",
				"font-size:" + _self.widthProportion() * _self.fontSize
						+ "px !important");
	}
	_self.changePage();
	window.addEventListener('resize', function() {
		_self.changePage();
	}, false);
};

window.onload = function() {
	FastClick.attach(document.body);
	var swiper = new Swiper('.swiper-container', {
		direction : 'vertical',
		speed : 800,
		onInit : function(swiper) {
			swiperAnimateCache(swiper);
			swiperAnimate(swiper);
		},
		onSlideChangeEnd : function(swiper) {
			swiperAnimate(swiper);
		}
	});
	var swiper = new Swiper('.banner', {
		pagination : '.swiper-pagination',
		paginationClickable : true,
		loop : true,
		autoplay : 3000,
		autoplayDisableOnInteraction : false,
	});
}