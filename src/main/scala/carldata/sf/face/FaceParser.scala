package carldata.sf.face

import carldata.sf.compiler.AST._

import scala.util.parsing.combinator.RegexParsers

/**
  * Parser for FlowWorks FACE formulas
  */
object FaceParser extends RegexParsers {

  def identifier: Parser[String] = """\w[a-zA-Z0-9_]?""".r
  def function: Parser[AppExpr] = identifier ~ "(" ~ funParams ~")" ^^ {
    x => AppExpr(x._1._1._1,x._1._2)
  }

  def funParams: Parser[Seq[Expression]] = repsep(addOrRelationExpr, ",")
  def number: Parser[NumberLiteral] = """-?\d+(\.\d*)?""".r ^^ { ds => NumberLiteral(ds.toFloat) }
  def variable: Parser[VariableExpr] = identifier ^^ { id => VariableExpr(id) }
  def factor: Parser[Expression] = function | number | variable | "(" ~> addOrRelationExpr <~ ")"
  def powExpr  : Parser[Expression] = factor ~ "^" ~ factor ^^ {
    case x ~ "^" ~ y => AppExpr("pow", Seq(x,y))
  }
  def powOrFactor: Parser[Expression] = powExpr | factor
  def multiExpr  : Parser[Expression] = powOrFactor ~ rep( "*" ~ powOrFactor | "/" ~ powOrFactor) ^^ {
    case number ~ list => (number /: list) {
      case (x, "*" ~ y) => BinaryOpExpr(x, "*", y)
      case (x, "/" ~ y) => BinaryOpExpr(x, "/", y)
    }
  }

  def addExpr  : Parser[Expression] = multiExpr ~ rep("+" ~ multiExpr | "-" ~ multiExpr) ^^ {
    case number ~ list => list.foldLeft(number) { // same as before, using alternate name for /:
      case (x, "+" ~ y) => BinaryOpExpr(x, "+", y)
      case (x, "-" ~ y) => BinaryOpExpr(x, "-", y)
    }
  }

  def relationExpr  : Parser[Expression] = addExpr ~ ("==" | ">=" | "<="  | "!=" | "<" | ">" ) ~ addExpr ^^ {
      x => RelationExpr(x._1._1, x._1._2,x._2)
  }

  def addOrRelationExpr: Parser[Expression] = relationExpr | addExpr

  def parse(input: String): Either[String, Expression] = parseAll(addOrRelationExpr, input) match {
    case Success(result, _) => Right(result)
    case failure : NoSuccess => Left(failure.msg)
  }
}