var Drawing = {};

Drawing.Element = function(config) {
  return new Kinetic.Rect({
    width: 30,
    height: 100,
    stroke: "black",
    strokeWidth: 1,
    draggable: true
  });
}

Drawing.Scheme = function(config) {

  /**
   * Private variables
   */
  var attributes = {
    width: 400,
    height: 150,
    interval: 10,
    xylinesWidth: 1
  };
  var stage = new Kinetic.Stage({
        container: config.container,
        width: attributes.width,
        height: attributes.height
      });
  var layer = new Kinetic.Layer();
  var elements = [];

  /**
   * Adds an element to the stage.
   */
  var _add = function(node) {
    var x = 5;
    var y = (stage.getHeight() - node.getHeight())/2;
    if(elements.length > 0) {
      var last = elements[elements.length - 1];
      x = last.getX() + last.getWidth() + attributes.interval;
    }
    node.setX(x);
    node.setY(y);
    elements.push(node);
    layer.add(node);
  };

  /**
   * Draws helper lines.
   */
  var _drawXYLines = function() {
    var xygroup = new Kinetic.Group();

    var xline = new Kinetic.Line({
      points: [
        {x: 0, y: stage.getHeight()/2},
        {x: stage.getWidth() - attributes.xylinesWidth, y: stage.getHeight()/2}
      ],
      stroke: "black",
      strokeWidth: 1
    });

    var yline = new Kinetic.Line({
      points: [
        {x: stage.getWidth() - attributes.xylinesWidth, y: stage.getHeight()*0.25},
        {x: stage.getWidth() - attributes.xylinesWidth, y: stage.getHeight()*0.75}
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
        _add(new Drawing.Element({}));
      }

      stage.add(layer);
    }
  };
};