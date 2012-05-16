var Drawing = Drawing || {};

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
    elementWidth: 30,
    elementHeight: 100
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
    var y = (this.stage.getHeight() - element.getHeight()) / 2,
        x = this.options.firstZSX,
        w = this.options.elementWidth;

    var previous = undefined;
    if(this.elements.length > 0){
        previous = this.elements[this.elements.length - 1];
    }

    if(element.getFirstZone() == "1") {
        if(previous != undefined) {
            x = previous.getX() + previous.getWidth() + this.options.interval;
        }
        if(element.getSecondZone() == "2") {
            x = this.options.secondZSX - this.options.elementWidth / 2;
        } else if(element.getSecondZone() == "3") {
            x = this.options.secondZSX - this.options.elementWidth / 2;
            w = this.options.thirdZSX + (this.options.elementWidth / 2) - x;
        }
    } else if(element.getFirstZone() == "2") {
        x = this.options.secondZSX + this.options.interval;
        if(element.getSecondZone() == "2") {
            x = this.options.centerX - this.options.elementWidth / 2;
        } else if(element.getSecondZone() == "3") {
            x = this.options.secondZEX - this.options.elementWidth / 2;
        }
    } else if(element.getFirstZone() == "3") {
        x = this.options.thirdZSX + this.options.interval;
        if(previous != undefined) {
            if((previous.getX() + previous.getWidth()) > this.options.thirdZSX) {
                x = previous.getX() + previous.getWidth() + this.options.interval;
            }
        }
    }

    //Set the element's props
    element.setX(x);
    element.setWidth(w);
    element.setY(y);

    this.elements.push(element);
    this.layer.add(element);
};

/**
 * @return one of [-1, 0, 1] numbers
 */
Drawing.Scheme.prototype._getSaliences = function (codes) {
    var results = [];
    var types = [];
    var zones = [];

    for(var i = 0; i < codes.length; i++) {
        types.push(codes[i].charAt(2));
        zones.push(parseInt(codes[i].charAt(1)));
        types.push(codes[i].charAt(4));
        zones.push(parseInt(codes[i].charAt(3)));
    }

    //console.log(types);
    //console.log(zones);

    var b = 0;

    for(var i = 0; i < types.length; i++) {
        if(types[i] == "P") {
            if(i % 2 != 0 && zones[i] >= zones[i - 1] && zones[i] > 1) {
                results[i] = 1;
            } else if(i % 2 == 0 && zones[i] > 1 && zones[i + 1] > zones[i]){
                results[i] = 1;
            } else {
                results[i] = -1;
            }
            if(i % 2 == 0){
                b = results[i];
            } else {
                b = -1 * results[i];
            }
        } else if(types[i] == "O") {
            results[i] = 0;
        } else if(types[i] == "A") {
            if(i == 0) {
                results[i] = 0;
            } else {
                if (b == 1) {
                    results[i] = 1;
                } else {
                    results[i] = -1;
                }
            }
        } else if(types[i] == "F") {
            if(b == 1) {
                results[i] = 1;
            } else {
                results[i] = -1;
            }
        } else {
            results[i] = 0;
        }
        //console.log(zones[i] + types[i] + ". a=" + results[i] + "; b=" + b);
    }
    //console.log(results);
    return results;
};

Drawing.Scheme.prototype.draw = function() {
    this._createXYLines();

    var surfaces = this._getSaliences(this.options.codes);

    for(var i = 0; i < this.options.codes.length; i++) {
        this._addElement(new Drawing.Element({
            width: this.options.elementWidth,
            height: this.options.elementHeight,
            code: this.options.codes[i],
            firstR: surfaces[i * 2],
            secondR: surfaces[i * 2 + 1]
        }));
    }

    this.stage.add(this.layer);
};