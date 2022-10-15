package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.entites.Product;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter the file path: ");
		String path = sc.next();
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			
			List<Product> list = new ArrayList<>();
			
			String line = br.readLine();
			while(line != null) {
				String[] fields = line.split(",");
				list.add(new Product(fields[0], Double.parseDouble(fields[1])));
				line = br.readLine();
			}

			double avg = list.stream()
					.map(p -> p.getValue())
					.reduce(0.0, (x,y) -> x + y) / list.size();
			System.out.println("Average: " + String.format("%.2f", avg));
			/* 1 - Acessa uma lista e transforma em uma Stream
			 * 2 - Aplica-se a função map p.getValue() para criar uma outra Stream só com os valores dos produtos
			 * 3 - Aplica-se a função reduce, que serve para somar itens de uma stream (Nesse caso, somando os valores)
			 * 4 - Divide os valores somados pela quantidade de itens na lista
			 * 5 - Mostra o valor médio na tela
			 */
			
			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
			/* 1 - Cria-se um comparator para os nomes do produto
			 * 2 - Será comparado um nome qualquer s1 e s2 (Remete a 's'trings)
			 * 3 - Converte eles em letras maísculas e os compara
			 */
			
			List<String> names = list.stream()
					.filter(p -> p.getValue() < avg)
					.map(p -> p.getName())
					.sorted(comp.reversed())
					.collect(Collectors.toList());
			names.forEach(System.out::println);
			/* 1 - Cria uma lista de Strings para criar uma stream de nomes
			 * 2 - Aplica-se a operação de filter para encontrar aqueles produtos com valores menores que a média
			 * 3 - Aplica-se a operação de map para filtrar essa nova stream por nomes
			 * 4 - Aplica-se a operação de sorted para mostrar os nomes em ordem, nesse caso reversa (.reverse)
			 * 5 - Usa-se a operação de collect para transformar essa stream em uma lista
			 * 6 - Acessamos a lista names e usamos um forEach para mostrar o resultado em tela
			 */
			
		}
		catch (IOException e){
			System.out.println("Error: " + e.getMessage());
			
		}
		
		finally {
			sc.close();
		}
	}

}
