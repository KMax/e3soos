var Drawing = Drawing || {};

Drawing.Element = function(config) {

    this.setDefaultAttrs({
        code: 'B1A1A',
        width: 0,
        height: 0,
        stroke: 'black',
        strokeWidth: 1,
        firstR: 0,
        secondR: 0
    });

    $.extend(this.attrs, config);

    this.shapeType = "Element";

    //Define attributes
    this.attrs.type = this.attrs.code.charAt(0);
    this.attrs.firstZone = this.attrs.code.charAt(1);
    this.attrs.firstSurface = this.attrs.code.charAt(2);
    this.attrs.secondZone = this.attrs.code.charAt(3);
    this.attrs.secondSurface = this.attrs.code.charAt(4);
    this.attrs.radious = 15;

    config.drawFunc = function() {

        this.attrs.centerX = this.attrs.width / 2;
        this.attrs.centerY = this.attrs.height / 2;

        var context = this.getContext();
        context.beginPath();
        if(this.attrs.firstR > 0) {
            context.moveTo(this.attrs.radious, 0);
        } else {
            context.moveTo(0,0);
        }
        if(this.attrs.secondR < 0) {
            context.lineTo(this.attrs.width - this.attrs.radious, 0);
            context.bezierCurveTo(this.attrs.width, this.attrs.radious,
                    this.attrs.width, this.attrs.height - this.attrs.radious,
                    this.attrs.width - this.attrs.radious, this.attrs.height);
        } else if(this.attrs.secondR > 0) {
            context.lineTo(this.attrs.width, 0);
            context.bezierCurveTo(this.attrs.width - this.attrs.radious, this.attrs.radious,
                    this.attrs.width - this.attrs.radious, this.attrs.height - this.attrs.radious,
                    this.attrs.width, this.attrs.height);
        } else {
            context.lineTo(this.attrs.width, 0);
            context.lineTo(this.attrs.width, this.attrs.height);
        }
        if(this.attrs.firstR > 0) {
            context.lineTo(this.attrs.radious, this.attrs.height);
            context.bezierCurveTo(0, this.attrs.height - this.attrs.radious,
                    0, this.attrs.radious,
                    this.attrs.radious, 0);
        } else if(this.attrs.firstR < 0) {
            context.lineTo(0, this.attrs.height);
            context.bezierCurveTo(this.attrs.radious, this.attrs.height - this.attrs.radious,
                    this.attrs.radious, this.attrs.radious,
                    0, 0);
        } else {
            context.lineTo(0, this.attrs.height);
            context.lineTo(0, 0);
        }
        context.closePath();
        this.applyStyles();
    };

    Kinetic.Shape.apply(this, [config]);
};

Drawing.Element.prototype = {
    getType : function () {
        return this.attrs.type;
    },
    getFirstZone: function () {
        return this.attrs.firstZone;
    },
    getFirstSurface: function () {
        return this.attrs.firstSurface;
    },
    setFirstR : function (r) {
        this.attrs.firstR = r;
    },
    setSecondR : function (r) {
        this.attrs.secondR = r;
    },
    getSecondZone: function () {
        return this.attrs.secondZone;
    },
    getSecondSurface: function () {
        return this.attrs.secondSurface;
    },
    /**
    * set width
    * @param {Number} width
    */
    setWidth: function(width) {
        this.attrs.width = width;
    },
    /**
    * get width
    */
    getWidth: function() {
        return this.attrs.width;
    },
    /**
    * set height
    * @param {Number} height
    */
    setHeight: function(height) {
        this.attrs.height = height;
    },
    /**
    * get height
    */
    getHeight: function() {
        return this.attrs.height;
    }
};

Kinetic.GlobalObject.extend(Drawing.Element, Kinetic.Shape);