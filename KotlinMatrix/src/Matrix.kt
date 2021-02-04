class Complex(var re: Double, var im: Double) {
    constructor(other: Complex) : this(other.re, other.im)
    constructor(re: Double) : this(re, 0.0)
    constructor() : this(0.0, 0.0)

    operator fun plus(rhs: Complex): Complex {
        return Complex(re + rhs.re, im + rhs.im)
    }

    operator fun minus(rhs: Complex): Complex {
        return Complex(re - rhs.re, im - rhs.im)
    }

    operator fun times(rhs: Complex): Complex {
        return Complex(re * rhs.re + im * rhs.im, re * rhs.im + im * rhs.re)
    }

    operator fun unaryMinus(): Complex {
        return Complex(-re, -im)
    }

    override fun toString(): String {
        return "($re, $im)"
    }
}

class Matrix(private var rows: Int, private var cols: Int) {
    var matrix: Array<Array<Complex>> = Array(rows) { Array(cols) { Complex() } }

    constructor(other: Matrix) : this(other.rows, other.cols) {
        matrix = other.matrix
    }

    operator fun get(i: Int, j: Int): Complex {
        return matrix[i][j]
    }

    operator fun set(i: Int, j: Int, value: Complex): Complex {
        matrix[i][j] = value
        return matrix[i][j]
    }

    operator fun plus(rhs: Matrix): Matrix {
        if (rows != rhs.rows || cols != rhs.cols) {
            throw Exception("Invalid Matrix size (rows, cols)")
        }
        val result = Matrix(rows, cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                result.matrix[i][j] = matrix[i][j] + rhs.matrix[i][j]
            }
        }
        return result
    }

    operator fun minus(rhs: Matrix): Matrix {
        if (rows != rhs.rows || cols != rhs.cols) {
            throw Exception("Invalid Matrix size (rows, cols)")
        }
        val result = Matrix(rows, cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                result.matrix[i][j] = matrix[i][j] - rhs.matrix[i][j]
            }
        }
        return result
    }

    operator fun times(rhs: Matrix): Matrix {
        if (cols != rhs.rows) {
            throw Exception("Invalid Matrix this.cols != rhs.rows")
        }
        val result = Matrix(rows, rhs.cols)
        for (i in 0 until rows) {
            for (j in 0 until rhs.cols) {
                for (k in 0 until cols) {
                    result.matrix[i][j] += matrix[i][k] * rhs.matrix[k][j]
                }
            }
        }
        return result
    }

    fun transpose(): Matrix {
        val result = Matrix(cols, rows)
        for (i in 0 until cols) {
            for (j in 0 until rows) {
                result.matrix[i][j] = matrix[j][i]
            }
        }
        return result
    }

    override fun toString(): String {
        var result = ""
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                result += matrix[i][j].toString()
                if (j != cols - 1) {
                    result += ", "
                }
            }
            result += "\n"
        }
        return result
    }
}

fun main() {
    val a = Matrix(2, 2)

    a[0, 0] = Complex()
    a[0, 1] = Complex(1.0)
    a[1, 0] = Complex(2.0, 1.0)
    a[1, 1] = Complex(1.0, 2.0)

    println("A:")
    println(a)
    println("A + A:")
    println(a + a)
    println("A - A")
    println(a - a)
    println("A * A")
    println(a * a)
    println("A.T")
    println(a.transpose())
}
