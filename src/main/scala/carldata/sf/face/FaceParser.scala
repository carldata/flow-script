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

  def funParams: Parser[Seq[Expression]] = repsep(expr, ",")
  def number: Parser[NumberLiteral] = """\d+(\.\d*)?""".r ^^ { ds => NumberLiteral(ds.toFloat) }
  def variable: Parser[VariableExpr] = identifier ^^ { id => VariableExpr(id) }
  def factor: Parser[Expression] = function | number | variable | "(" ~> expr <~ ")"
  def pow  : Parser[Expression] = factor ~ "^" ~ factor ^^ {
    case x ~ "^" ~ y => AppExpr("pow", Seq(x,y))
  }
  def powOrfactor: Parser[Expression] = pow | factor
  def term  : Parser[Expression] = powOrfactor ~ rep( "*" ~ powOrfactor | "/" ~ powOrfactor) ^^ {
    case number ~ list => (number /: list) {
      case (x, "*" ~ y) => BinaryOpExpr(x, "*", y)
      case (x, "/" ~ y) => BinaryOpExpr(x, "/", y)
    }
  }

  def expr  : Parser[Expression] = term ~ rep("+" ~ term | "-" ~ term) ^^ {
    case number ~ list => list.foldLeft(number) { // same as before, using alternate name for /:
      case (x, "+" ~ y) => BinaryOpExpr(x, "+", y)
      case (x, "-" ~ y) => BinaryOpExpr(x, "-", y)
    }
  }

  def parse(input: String): Either[String, Expression] = parseAll(expr, input) match {
    case Success(result, _) => Right(result)
    case failure : NoSuccess => Left(failure.msg)
  }
}
