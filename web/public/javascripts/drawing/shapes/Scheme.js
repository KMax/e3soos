var Drawing = Drawing || {};

/**
 * A structural scheme.
 */
Drawing.Scheme = function(options) {
    //Initialize options
    this.options = {};
    $.extend(this.options, this._defaults, options);

    this.layer = new Kinetic.Layer();
    this.elements = [];
    this.surfaces = [];
    this.types = [];
    this.zones = [];
    this.inFirstZone = 0;
    this.inSecondZone = 0;
    this.inThirdZone = 0;

    for(var i = 0; i < this.options.codes.length; i++) {
        this.types.push(this.options.codes[i].charAt(2));
        this.zones.push(parseInt(this.options.codes[i].charAt(1)));
        this.types.push(this.options.codes[i].charAt(4));
        this.zones.push(parseInt(this.options.codes[i].charAt(3)));
        if(this.zones[i*2] == 1 && this.zones[i*2 + 1] == 1) {
            this.inFirstZone++;
        }
        if(this.zones[i*2] == 2 && this.zones[i*2 + 1] == 2) {
            this.inSecondZone++;
        }
        if(this.zones[i*2] == 3 && this.zones[i*2 + 1] == 3) {
            this.inThirdZone++;
        }
    }

    this.options.firstZoneW = this.options.elementWidth * (1 + this.inFirstZone)
        + this.options.interval * (1 + this.inFirstZone);
    this.options.firstZSX = this.options.interval;
    this.options.secondZoneW = this.options.elementWidth * (3 + this.inSecondZone)
        + this.options.interval * (3 + this.inSecondZone);
    this.options.secondZSX = this.options.firstZoneW + this.options.interval;
    this.options.secondZEX = this.options.secondZSX + this.options.secondZoneW;
    this.options.thirdZoneW = this.options.elementWidth * (1 + this.inThirdZone)
        + this.options.interval * (1 + this.inThirdZone);
    this.options.thirdZSX = this.options.secondZEX + this.options.interval,

    this.options.width = this.options.firstZoneW + this.options.interval * 2 + this.options.secondZoneW
        + this.options.thirdZoneW;
    this.options.centerX = this.options.secondZSX + this.options.secondZoneW / 2;
    this.options.centerY = this.options.height / 2;
    this.options.endX = this.options.width - this.options.xylinesStrokeWidth;
    this.options.zoneBottomY = (this.options.height - this.options.xylinesStrokeWidth * 2) * 0.95;
};

Drawing.Scheme.prototype._defaults = {
    container: '',
    codes: [],
    width: 200,
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
        {x: this.options.endX, y: this.options.height * 0.25},
        {x: this.options.endX, y: this.options.height * 0.75}
      ],
      stroke: this.options.xylinesStroke,
      strokeWidth: this.options.xylinesStrokeWidth
    }));

    //DiaTop
    xygroup.add(new Kinetic.Line({
      points: [
        {x: this.options.centerX, y: this.options.height * 0.3},
        {x: this.options.centerX, y: this.options.height * 0.4}
      ],
      stroke: this.options.xylinesStroke,
      strokeWidth: this.options.xylinesStrokeWidth
    }));

    //DiaBottom
    xygroup.add(new Kinetic.Line({
      points: [
        {x: this.options.centerX, y: this.options.height * 0.7},
        {x: this.options.centerX, y: this.options.height * 0.6}
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
    var y = (this.options.height - element.getHeight()) / 2,
        x = this.options.firstZSX,
        w = this.options.elementWidth;

    var previous = undefined;
    if(this.elements.length > 0) {
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
            w = this.options.secondZEX + (this.options.elementWidth / 2) - x;
        }
    } else if(element.getFirstZone() == "2") {
        x = this.options.centerX + this.options.interval;
        if(element.getSecondZone() == "2" && previous != undefined) {
            if(previous.getFirstZone() == "2" && previous.getSecondZone() == "2") {
                x = previous.getX() + previous.getWidth() + this.options.interval;
            }
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
Drawing.Scheme.prototype._getSaliences = function () {
    var results = [];

    //console.log(types);
    //console.log(zones);

    var b = 0;

    for(var i = 0; i < this.types.length; i++) {
        if(this.types[i] == "P") {
            if(i % 2 != 0 && this.zones[i - 1] == 1 && this.zones[i] < 3) {
                results[i] = 1;
            } else if(i % 2 == 0 && this.zones[i] < 2){
                results[i] = 1;
            } else {
                results[i] = -1;
            }
            if(i % 2 == 0){
                b = results[i];
            } else {
                b = -1 * results[i];
            }
        } else if(this.types[i] == "O") {
            results[i] = 0;
        } else if(this.types[i] == "A") {
            if(i == 0) {
                results[i] = 0;
            } else {
                if (b == 1) {
                    results[i] = 1;
                } else {
                    results[i] = -1;
                }
            }
        } else if(this.types[i] == "F") {
            if(b == 1) {
                results[i] = 1;
            } else {
                results[i] = -1;
            }
        } else if(this.types[i] == "V" && this.zones[i] == 2 && i == 0){
            results[i] = -1;
        } else {
            results[i] = 0;
        }
        //console.log(zones[i] + types[i] + ". a=" + results[i] + "; b=" + b);
    }
    //console.log(results);
    return results;
};

Drawing.Scheme.prototype.draw = function() {

    this.surfaces = this._getSaliences(this.options.codes);

    for(var i = 0; i < this.options.codes.length; i++) {
        this._addElement(new Drawing.Element({
            width: this.options.elementWidth,
            height: this.options.elementHeight,
            code: this.options.codes[i],
            firstR: this.surfaces[i * 2],
            secondR: this.surfaces[i * 2 + 1]
        }));
    }

    this._createXYLines();

    //Create a stage
    var stage = new Kinetic.Stage({
                    container: this.options.container,
                    width: this.options.width,
                    height: this.options.height
                });

    stage.add(this.layer);
};