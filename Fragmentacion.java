public class SplitFile {
 
    public static void main(String[] args) throws IOException {
        getSplitFile();
                 String file = SplitFileParam.file; // Ruta del archivo
        RandomAccessFile raf = null;
        raf = new RandomAccessFile(new File(file), "r");
                 long length = raf.length (); // La longitud total del archivo
                 long maxSize = SplitFileParam.maxSize; // La longitud del archivo después de cortar
                 long count = length / maxSize; // Número de copias del archivo dividido
        merge(SplitFileParam.outfile,SplitFileParam.file,count);
    }
 
    /**
           * Método de división de archivos
     */
    public static void getSplitFile() {
                 String file = SplitFileParam.file; // Ruta del archivo
 
        RandomAccessFile raf = null;
        try {
                         // Obtener el archivo de destino y preasignar el espacio ocupado por el archivo Crear un archivo del tamaño especificado en el disco r es de solo lectura
            raf = new RandomAccessFile(new File(file), "r");
                         long length = raf.length (); // La longitud total del archivo
                         long maxSize = SplitFileParam.maxSize; // La longitud del archivo después de cortar
 // cuenta larga = longitud / tamaño max; // El número de copias de la división del archivo
                         long count = length / maxSize; // Número de copias del archivo dividido
                         long offSet = 0L; // Inicializar el desplazamiento
                         for (long i = 0; i <count; i ++) {// La última pieza se procesa por separado La cuenta que calculé así es la última pieza eliminada 
                long begin = offSet;
                long end = (i + 1) * maxSize;
//                offSet = writeFile(file, begin, end, i);
                offSet = getWrite(file, i, begin, end);
            }
            if (length - offSet > 0) {
                getWrite(file, count, offSet, length);
            }
 
        } catch (FileNotFoundException e) {
                         System.out.println ("Archivo no encontrado");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
           * Especifique el límite de cada copia del archivo y escríbalo en un archivo diferente
           * @param archivo fuente de archivo
           * @param i El ID de pedido del archivo fuente
           * @param comienza la posición del puntero de inicio
           * @param finaliza la posición del puntero final
     * @return long
     */
    public static long getWrite(String file,long i,long begin,long end){
        String a=file.split(".mp3")[0];
        long endPointer = 0L;
        byte[] data = null;
        try {
                         // Declarar el disco de archivos después de cortar el archivo
            RandomAccessFile in = new RandomAccessFile(new File(file), "r");
                         // Defina un archivo legible y escribible y un archivo binario con la extensión .tmp
            RandomAccessFile out = new RandomAccessFile(new File(a + "_" + i + ".tmp"), "rw");
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
                         // Declara la matriz de bytes de cada archivo
            byte[] b = new byte[1024];
            int n = 0;
                         // Leer el flujo de bytes del archivo desde la posición especificada
            in.seek(begin);
                         // Determine el límite de la lectura del flujo de archivos
            while(in.getFilePointer() < end && (n = in.read(b)) != -1){
                if(in.getFilePointer() > end && in.getFilePointer() < 615420 ) {
                    System.out.println(in.getFilePointer());
                }
                                 // Del rango especificado de cada archivo, escriba un archivo diferente
//                baos.write(b, 0, n);
                out.write(b, 0, n);
                
            }
            data = baos.toByteArray();
            String str = new String(data,"UTF-8");
                         // Definir el puntero del archivo leído actualmente
            endPointer = in.getFilePointer();
                         // Cerrar el flujo de entrada
            in.close();
                         // Cerrar el flujo de salida
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return endPointer;
    }
    /**
           * Fusión de archivos
           * El archivo @param especifica el archivo de combinación
           * @param tempFile Nombre de archivo antes de dividir
           * @param cuenta el número de archivos
     */
    public static void merge(String file,String tempFile,long count) {
        String a=tempFile.split(".mp3")[0];
        RandomAccessFile raf = null;
        try {
                         // Declarar RandomAccessFile RandomAccessFile
            raf = new RandomAccessFile(new File(file), "rw");
                         // Comienza a fusionar archivos, correspondientes al archivo binario cortado
            for (int i = 0; i < count+1; i++) {
                                 // Leer el archivo de corte
                RandomAccessFile reader = new RandomAccessFile(new File(a + "_" + i + ".tmp"), "r");
                byte[] b = new byte[1024];
                int n = 0;
                                 // Leer primero y escribir después
                                 while ((n = reader.read (b))! = -1) {// leer
                                         raf.write (b, 0, n); // escribir
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }