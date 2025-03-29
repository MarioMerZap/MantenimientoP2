package org.mps.tree;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;

import org.junit.jupiter.api.*;

@DisplayName("Tests para BinarySearchTree")
public class BinarySearchTreeTest {
    private BinarySearchTree<Integer> arbol;

    @BeforeEach
    public void setUp() {
        arbol = new BinarySearchTree<Integer>(Comparator.naturalOrder());
    }

    @Nested
    @DisplayName("Pruebas de inserts")
    class InsertsTest {

        @Test
        @DisplayName("Insertar un número menor como hijo izquierdo")
        public void insertarNumeroMenor() {

            arbol.insert(10);

            arbol.insert(5);

            assertEquals("10(5,)", arbol.render());
        }
        
        @Test
        @DisplayName("El método render() devuelve String vacío si el árbol está vacío")
        public void renderDevuelveCadenaVaciaSiArbolVacio() {
        // Arrange → No insertamos nada, el árbol está vacío

        String resultado = arbol.render();

        assertEquals("", resultado);
}


        @Test
        @DisplayName("Insertar un número mayor como hijo derecho")
        public void insertarNumeroMayor() {

            arbol.insert(10);

            arbol.insert(15);

            assertEquals("10(,15)", arbol.render());
        }
        @Test
        @DisplayName("Insertar un número mayor cuando ya existe subárbol derecho")
        public void insertEnSubarbolDerecho() {
            arbol.insert(10); // Raíz
            arbol.insert(15); // Se inserta en la derecha

            arbol.insert(20); // Se inserta en el subárbol derecho del 15

            assertEquals("10(,15(,20))", arbol.render());
        }

        @Test
        @DisplayName("El método insert() lanza excepción si el valor ya está en el árbol")
        public void insertLanzaExcepcionSiValorDuplicado() {
        arbol.insert(10);
        assertThrows(BinarySearchTreeException.class, () -> arbol.insert(10));
        }

    }

    @Nested
    @DisplayName("Pruebas de mínimo y máximo")
    class TestMinimoMaximo {

        @Test
        @DisplayName("El método minimum() devuelve el menor valor del árbol")
        public void minimoDevuelveElValorMasPequeno() {

            arbol.insert(10);
            arbol.insert(5);
            arbol.insert(15);
            arbol.insert(2);

            int minimo = arbol.minimum();

            assertEquals(2, minimo);
        }

        @Test
        @DisplayName("El método minimum() lanza excepcion si el valor es nulo")
        public void minimumLanzaExcepcionSiValorNulo(){
            assertThrows(BinarySearchTreeException.class,() -> arbol.minimum());

        }

        @Test
        @DisplayName("El método maximum() devuelve el mayor valor del árbol")
        public void maximoDevuelveElValorMasGrande() {

            arbol.insert(10);
            arbol.insert(5);
            arbol.insert(15);
            arbol.insert(2);

            int maximo = arbol.maximum();

            assertEquals(15, maximo);
        }

        @Test
        @DisplayName("El método maximum() lanza excepcion si el valor es nulo")
        public void maximumLanzaExcepcionSiValorNulo(){
            assertThrows(BinarySearchTreeException.class,() -> arbol.maximum());
        }
    }

    @Nested
    @DisplayName("Pruebas de nodo hoja")
    class NodoHojaTest {

        @Test
        @DisplayName("Un nodo sin hijos es una hoja")
        public void nodoEsHojaSiNoTieneHijos() {
            arbol.insert(10);

            boolean esHoja = arbol.isLeaf();

            assertTrue(esHoja);
        }

        @Test
        @DisplayName("Un nodo con hijo izquierdo no es una hoja")
        public void nodoNoEsHojaSiTieneHijoIzquierdo() {

            arbol.insert(10);
            arbol.insert(5);

            boolean esHoja = arbol.isLeaf();

            assertFalse(esHoja);
        }

        @Test
        @DisplayName("Un nodo con hijo derecho no es una hoja")
        public void nodoNoEsHojaSiTieneHijoDerecho() {

            arbol.insert(10);
            arbol.insert(15);

            boolean esHoja = arbol.isLeaf();

            assertFalse(esHoja);
        }

        @Test
        @DisplayName("Un nodo con dos hijos no es una hoja")
        public void nodoNoEsHojaSiTieneDosHijos() {
            
            arbol.insert(10);
            arbol.insert(5);
            arbol.insert(15);

            boolean esHoja = arbol.isLeaf();
            
            assertFalse(esHoja);
        }

        @Test
        @DisplayName("El método isLeaf() lanza una excepción si el árbol está vacío")
        public void isLeafLanzaExcepcionSiElArbolEstaVacio() {
            assertThrows(BinarySearchTreeException.class, () -> arbol.isLeaf());
        }
    }
    
    @Nested
    @DisplayName("Pruebas de Contains")
    class TestsContains{
        
        @Test
        @DisplayName("El método contains() devuelve false si el árbol está vacío")
        public void containsDevuelveFalseSiArbolVacio() {
        // Arrange → No insertamos ningún valor, el árbol está vacío

        boolean resultado = arbol.contains(10);

        assertFalse(resultado);
        }

        @Test
        @DisplayName("Valor esta contenido en el arbol")
        public void valorEstaContenido(){
            arbol.insert(10);

            boolean resultado = arbol.contains(10);

            assertTrue(resultado);
        }
        
        @Test
        @DisplayName("El método contains() devuelve true si el valor está en el subárbol izquierdo")
            public void containsDevuelveTrueSiValorEstaEnSubarbolIzquierdo() {
            arbol.insert(10);
            arbol.insert(5);

            boolean resultado = arbol.contains(5);

            assertTrue(resultado);
        }

        @Test
        @DisplayName("El método contains() devuelve true si el valor está en el subárbol izquierdo")
        public void containsDevuelveTrueSiValorEstaEnSubarbolDerecho() {
            arbol.insert(10);
            arbol.insert(15);

            boolean resultado = arbol.contains(15);

            assertTrue(resultado);
        }

        @Test
        @DisplayName("El método contains() devuelve false si el valor no está en el árbol")
        public void containsDevuelveFalseSiValorNoEstaEnArbol() {
            arbol.insert(10);
            arbol.insert(5);
            arbol.insert(15);

            boolean resultado = arbol.contains(20); // No está en el árbol

            assertFalse(resultado);
        }
        @Test
        @DisplayName("El método contains() devuelve false si el nodo es hoja y el valor no coincide")
        public void containsDevuelveFalseSiNodoEsHojaYValorNoCoincide() {
            arbol.insert(10); // Árbol con solo un nodo (es hoja)
        
            boolean resultado = arbol.contains(5); // 5 no está en el árbol
        
            assertFalse(resultado);
        }

        @Test
        @DisplayName("El método contains() devuelve true si el valor está en el subárbol izquierdo")
        public void containsDevuelveTrueSiValorEstaEnSubarbolIzquierdoConRecursion() {
        // Arrange
        arbol.insert(10);
        arbol.insert(5);
        arbol.insert(3); // Nodo más profundo en la izquierda

        // Act
        boolean resultado = arbol.contains(3); // Buscar en el subárbol izquierdo profundo

        // Assert
        assertTrue(resultado);
        }

        


    }

    @Nested
    @DisplayName("Pruebas de Remove")
    class TestsRemove{
        @Test
        @DisplayName("removeBranch lanza excepción si el arbol está vacío")
        public void removeBranch_ArbolVacio_LanzaExcepcion() {
            assertThrows(BinarySearchTreeException.class, () -> arbol.removeBranch(10));
        }

        @Test
        @DisplayName("removeBranch elimina la raíz y deja el árbol vacío")
        public void removeBranch_EliminaRaiz_DejaArbolVacio() {
            //Arrange
            arbol.insert(10);

            //Act
            arbol.removeBranch(10);

            //Assert
            assertEquals("", arbol.render());
        }

        @Test
        @DisplayName("removeBranch elimina un nodo con un solo hijo")
        public void removeBranch_EliminaNodoConUnHijo() {
            //Arrange
            arbol.insert(10);
            arbol.insert(5);

            //Act
            arbol.removeBranch(10);

            //Assert
            assertEquals("5", arbol.render());
        }

        @Test
        @DisplayName("removeBranch elimina un nodo con dos hijos y su subárbol")
        public void removeBranch_EliminaNodoConDosHijosYSubarbol() {
            //Arrange
            arbol.insert(10);
            arbol.insert(5);
            arbol.insert(15);
            arbol.insert(3);
            arbol.insert(7);
            arbol.insert(12);
            arbol.insert(17);

            //Act
            arbol.removeBranch(10);

            //Assert
            assertEquals("12(5(3,7),15(,17))", arbol.render());
        }

        @Test
        @DisplayName("removeBranch no modifica el árbol si el nodo no existe")
        public void removeBranch_NodoNoExiste_NoModificaArbol() {
            //Arrange
            arbol.insert(10);
            arbol.insert(5);
            arbol.insert(15);

            //Act
            arbol.removeBranch(20);

            //Assert
            assertEquals("10(5,15)", arbol.render());
        }
    }

    @Nested
    @DisplayName("Pruebas de Tamaño")
    class TestsSize{
        @Test
        @DisplayName("El método size() devuelve 0 si el árbol está vacío")
            public void sizeDevuelveCeroSiArbolEstaVacio() {
                // Arrange → El árbol está vacío, no se inserta ningún valor
                // Act
                int resultado = arbol.size();

                // Assert
                assertEquals(0, resultado);
        }

        @Test
        @DisplayName("El método size() devuelve 1 si el árbol tiene solo la raíz")
            public void sizeDevuelveUnoSiArbolTieneSoloRaiz() {
                // Arrange → El árbol tiene un solo nodo (la raíz)
                arbol.insert(10);

                // Act
                int resultado = arbol.size();

                // Assert
                assertEquals(1, resultado);
        }

        @Test
        @DisplayName("El método size() devuelve el número correcto de nodos en un árbol con varios nodos")
            public void sizeDevuelveNumeroCorrectoDeNodos() {
                // Arrange → El árbol tiene varios nodos
                arbol.insert(10);
                arbol.insert(5);
                arbol.insert(15);
                arbol.insert(3);
                arbol.insert(7);

                // Act
                int resultado = arbol.size();

                // Assert
                assertEquals(5, resultado);
        }

}

    @Nested
    @DisplayName("Pruebas de Profundidad")
    class TestsDepth{
        @Test
        @DisplayName("El método depth() devuelve 0 si el árbol está vacío")
        public void depthDevuelveCeroSiArbolEstaVacio() {
            // Arrange → El árbol está vacío
            // Act
            int resultado = arbol.depth();
        
            // Assert
            assertEquals(0, resultado);
        }
        
        @Test
        @DisplayName("El método depth() devuelve 1 si el árbol tiene solo la raíz")
        public void depthDevuelveUnoSiArbolTieneSoloRaiz() {
            // Arrange → El árbol tiene un solo nodo (la raíz)
            arbol.insert(10);
        
            // Act
            int resultado = arbol.depth();
        
            // Assert
            assertEquals(1, resultado);
        }
        
        @Test
        @DisplayName("El método depth() devuelve la profundidad correcta en un árbol balanceado")
        public void depthDevuelveProfundidadCorrectaEnArbolBalanceado() {
            // Arrange → El árbol tiene una estructura balanceada
            arbol.insert(10);
            arbol.insert(5);
            arbol.insert(15);
        
            // Act
            int resultado = arbol.depth();
        
            // Assert
            assertEquals(2, resultado); // Profundidad 2 porque el árbol es balanceado
        }
        
        @Test
        @DisplayName("El método depth() devuelve la profundidad correcta en un árbol desbalanceado")
        public void depthDevuelveProfundidadCorrectaEnArbolDesbalanceado() {
            // Arrange → El árbol tiene una estructura desbalanceada
            arbol.insert(10);
            arbol.insert(5);
            arbol.insert(3);
            arbol.insert(2);
        
            // Act
            int resultado = arbol.depth();
        
            // Assert
            assertEquals(4, resultado); // Profundidad 4 debido al subárbol izquierdo desbalanceado
        }
    
    @Nested
    @DisplayName("Pruebas de inOrder y balance")
    class TestsInOrderYBalance {
        
        @Test
        @DisplayName("El método inOrder() devuelve los valores en orden ascendente")
        public void inOrderDevuelveValoresOrdenados() {
            arbol.insert(10);
            arbol.insert(5);
            arbol.insert(15);
            arbol.insert(2);
            arbol.insert(7);
            
            List<Integer> resultado = arbol.inOrder();
            
            assertEquals(Arrays.asList(2, 5, 7, 10, 15), resultado);
        }
        
        @Test
        @DisplayName("El método inOrder() devuelve una lista vacía si el árbol está vacío")
        public void inOrderDevuelveListaVaciaSiArbolVacio() {
            List<Integer> resultado = arbol.inOrder();
            
            assertTrue(resultado.isEmpty());
        }
        
        @Test
        @DisplayName("El método balance() convierte el árbol en un árbol balanceado")
        public void balanceConvierteArbolEnBalanceado() {
            arbol.insert(10);
            arbol.insert(5);
            arbol.insert(15);
            arbol.insert(2);
            arbol.insert(7);
            arbol.insert(12);
            arbol.insert(17);
            
            arbol.balance();
            
            int profundidad = arbol.depth();
            
            assertEquals(3, profundidad); // Un árbol balanceado debería tener profundidad óptima
        }
        
        @Test
        @DisplayName("El método balance() no modifica un árbol ya balanceado")
        public void balanceNoModificaArbolYaBalanceado() {
            arbol.insert(10);
            arbol.insert(5);
            arbol.insert(15);
            
            arbol.balance();
            
            assertEquals("10(5,15)", arbol.render());
        }
    }
    

}

}

