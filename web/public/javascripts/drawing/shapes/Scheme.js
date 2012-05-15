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
Drawing.Scheme = function(config) {

  /**
   * Private variables
   */
  var attrs = {
    width: 400,
    height: 150,
    interval: 20,
    xylinesWidth: 1
  };
  var stage = new Kinetic.Stage({
        container: config.container,
        width: attrs.width,
        height: attrs.height
      });
  var layer = new Kinetic.Layer();
  var elements = [];

    /**
    * Adds an optical element to the scene.
    */
    var _add = function (element) {
        var y = (stage.getHeight() - element.shape.getHeight())/2,
            secondZS = stage.getWidth()/2 - 50,
            secondZE = stage.getWidth()/2 + 50;
        var firstZS = attrs.interval,
            firstZE = secondZS - attrs.interval,
            thirdZS = secondZE + attrs.interval,
            thirdZE = stage.getWidth() - attrs.interval;
        var x = firstZS,
            w = element.shape.getWidth();

        if(element.attrs.firstZone == "1") {
            if(elements.length > 0) {
                var last = elements[elements.length - 1];
                x = last.shape.getX() + last.shape.getWidth() + attrs.interval;
            }
            if(element.attrs.secondZone == "2") {
                w = secondZS - x;
            } else if(element.attrs.secondZone == "3") {
                w = thirdZS - x;
            }
        } else if(element.attrs.firstZone == "2") {
            x = secondZS + attrs.interval;
            if(element.attrs.secondZone == "2") {
                w = secondZE - secondZS;
            } else if(element.attrs.secondZone == "3") {
                w = thirdZS - x;
            }
        } else if(element.attrs.firstZone == "3") {
            var last = elements[elements.length - 1];
            if(last.shape.getX() > thirdZS) {
                x = last.shape.getX() + last.shape.getWidth() + attrs.interval;
            } else {
                x = thirdZS + attrs.interval;
            }
        }

        element.shape.setX(x);
        element.shape.setWidth(w);
        element.shape.setY(y);

        elements.push(element);
        layer.add(element.shape);
    };

  /**
   * Draws helper lines.
   */
  var _drawXYLines = function() {
    var xygroup = new Kinetic.Group();

    var xline = new Kinetic.Line({
      points: [
        {x: 0, y: stage.getHeight()/2},
        {x: stage.getWidth() - attrs.xylinesWidth, y: stage.getHeight()/2}
      ],
      stroke: "black",
      strokeWidth: 1
    });

    var yline = new Kinetic.Line({
      points: [
        {x: stage.getWidth() - attrs.xylinesWidth, y: stage.getHeight()*0.25},
        {x: stage.getWidth() - attrs.xylinesWidth, y: stage.getHeight()*0.75}
      ],
      stroke: "black",
      strokeWidth: 1
    });

    var diaTop = new Kinetic.Line({
      points: [
        {x: stage.getWidth()/2, y: stage.getHeight()*0.3 },
        {x: stage.getWidth()/2, y: stage.getHeight()*0.4 }
      ],
      stroke: "black",
      strokeWidth: 1
    });

    var diaBottom = new Kinetic.Line({
      points: [
        {x: stage.getWidth()/2, y: stage.getHeight()*0.7 },
        {x: stage.getWidth()/2, y: stage.getHeight()*0.6 }
      ],
      stroke: "black",
      strokeWidth: 1
    });

    xygroup.add(xline);
    xygroup.add(yline);
    xygroup.add(diaTop);
    xygroup.add(diaBottom);

    layer.add(xygroup);
  };

  /**
  * Public methods and variables
  */
  return {
    draw: function() {

      _drawXYLines();

      for(var i = 0; i < config.elements.length; i++) {
        _add(new Drawing.Element({
            code: config.elements[i]
        }));
      }

      stage.add(layer);
    }
  };
};