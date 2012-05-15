var Drawing = Drawing || {};

/**
 * An optical element.
 */
Drawing.Element = function (options) {

    this.options = {};
    $.extend(this.options, this.defaults, options);

    this.attrs = {
        type: this.options.code.charAt(0),
        firstZone: this.options.code.charAt(1),
        firstSurface: this.options.code.charAt(2),
        secondZone: this.options.code.charAt(3),
        secondSurface: this.options.code.charAt(4)
    };

    this.shape = new Kinetic.Rect(this.options);
};

Drawing.Element.prototype.defaults = {
    code: 'B1A1A',
    width: 30,
    height: 100,
    stroke: 'black',
    strokeWidth: 1,
    draggable: true
};

/**
 * A structural scheme.
 */
Drawing.Scheme = function(options) {
    //Initialize options
    this.options = {};
    $.extend(this.options, this._defaults, options);

    this.stage = new Kinetic.Stage({
                    container: this.options.container,
                    width: this.options.width,
                    height: this.options.height
                });

    //Expand the options
    this.options.centerX = this.stage.getWidth() / 2;
    this.options.centerY = this.stage.getHeight() / 2;
    this.options.endX = this.stage.getWidth() - this.options.xylinesStrokeWidth;
    this.options.secondZoneWidth = this.options.elementWidth * 2 + this.options.interval * 2;
    this.options.secondZSX = this.options.centerX - (this.options.secondZoneWidth / 2);
    this.options.secondZEX = this.options.centerX + (this.options.secondZoneWidth / 2);
    this.options.firstZSX = this.options.interval,
    this.options.firstZEX = this.options.secondZSX,
    this.options.thirdZSX = this.options.secondZEX,
    this.options.thirdZEX = this.stage.getWidth() - this.options.interval;
    this.options.zoneBottomY = (this.stage.getHeight() - this.options.xylinesStrokeWidth * 2) * 0.95;

    this.layer = new Kinetic.Layer();
    this.elements = [];
};

Drawing.Scheme.prototype._defaults = {
    container: '',
    codes: [],
    width: 400,
    height: 150,
    interval: 20,
    xylinesStrokeWidth: 1,
    xylinesStroke: "black",
    elementWidth: 30
};

Drawing.Scheme.prototype._createXYLines = function () {
    var xygroup = new Kinetic.Group();

    //XLine
    xygroup.add(new Kinetic.Line({
      points: [
          {x: 0, y: this.options.centerY},
          {x: this.options.endX,y: this.options.centerY}
      ],
      stroke: this.options.xylinesStroke,
      strokeWidth: this.options.xylinesStrokeWidth
    }));

    //YLine
    xygroup.add(new Kinetic.Line({
      points: [
        {x: this.options.endX, y: this.stage.getHeight() * 0.25},
        {x: this.options.endX, y: this.stage.getHeight() * 0.75}
      ],
      stroke: this.options.xylinesStroke,
      strokeWidth: this.options.xylinesStrokeWidth
    }));

    //DiaTop
    xygroup.add(new Kinetic.Line({
      points: [
        {x: this.options.centerX, y: this.stage.getHeight() * 0.3},
        {x: this.options.centerX, y: this.stage.getHeight() * 0.4}
      ],
      stroke: this.options.xylinesStroke,
      strokeWidth: this.options.xylinesStrokeWidth
    }));

    //DiaBottom
    xygroup.add(new Kinetic.Line({
      points: [
        {x: this.options.centerX, y: this.stage.getHeight() * 0.7},
        {x: this.options.centerX, y: this.stage.getHeight() * 0.6}
      ],
      stroke: this.options.xylinesStroke,
      strokeWidth: this.options.xylinesStrokeWidth
    }));

    var secondZone = new Kinetic.Group();
    secondZone.add(new Kinetic.Line({
        points: [
            {x: this.options.secondZSX, y: this.options.zoneBottomY},
            {x: this.options.secondZEX, y: this.options.zoneBottomY}
        ],
        stroke: this.options.xylinesStroke,
        strokeWidth: this.options.xylinesStrokeWidth
    }));
    secondZone.add(new Kinetic.Line({
        points: [
            {x: this.options.secondZSX, y: this.options.zoneBottomY},
            {x: this.options.secondZSX, y: this.options.zoneBottomY * 0.95}
        ],
        stroke: this.options.xylinesStroke,
        strokeWidth: this.options.xylinesStrokeWidth
    }));
    secondZone.add(new Kinetic.Line({
        points: [
            {x: this.options.secondZEX, y: this.options.zoneBottomY},
            {x: this.options.secondZEX, y: this.options.zoneBottomY * 0.95}
        ],
        stroke: this.options.xylinesStroke,
        strokeWidth: this.options.xylinesStrokeWidth
    }));

    this.layer.add(xygroup);
    this.layer.add(secondZone);
};

Drawing.Scheme.prototype._addElement = function (element) {
    //The element's props
    var y = (this.stage.getHeight() - element.shape.getHeight())/2,
        x = this.options.firstZSX,
        w = this.options.elementWidth;

    var previous = undefined;
    if(this.elements.length > 0){
        previous = this.elements[this.elements.length - 1];
    }

    if(element.attrs.firstZone == "1") {
        if(previous != undefined) {
            x = previous.shape.getX() + previous.shape.getWidth() + this.options.interval;
        }
        if(element.attrs.secondZone == "2") {
            x = this.options.secondZSX - this.options.elementWidth / 2;
        } else if(element.attrs.secondZone == "3") {
            x = this.options.secondZSX - this.options.elementWidth / 2;
            w = this.options.thirdZSX + (this.options.elementWidth / 2) - x;
        }
    } else if(element.attrs.firstZone == "2") {
        x = this.options.secondZSX + this.options.interval;
        if(element.attrs.secondZone == "2") {
            x = this.options.centerX - this.options.elementWidth / 2;
        } else if(element.attrs.secondZone == "3") {
            x = this.options.secondZEX - this.options.elementWidth / 2;
        }
    } else if(element.attrs.firstZone == "3") {
        x = this.options.thirdZSX + this.options.interval;
        if(previous != undefined) {
            if((previous.shape.getX() + previous.shape.getWidth()) > this.options.thirdZSX) {
                x = previous.shape.getX() + previous.shape.getWidth() + this.options.interval;
            }
        }
    }

    //Set the element's props
    element.shape.setX(x);
    element.shape.setWidth(w);
    element.shape.setY(y);

    this.elements.push(element);
    this.layer.add(element.shape);
};

Drawing.Scheme.prototype.draw = function() {
    this._createXYLines();

    for(var i = 0; i < this.options.codes.length; i++) {
        this._addElement(new Drawing.Element({code: this.options.codes[i]}));
    }

    this.stage.add(this.layer);
};