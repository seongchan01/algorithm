import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * [문제 해석]
 * 각 변에 16진수 숫자(0~F) 적혀 있는 보물상자
 * 보물상자의 뚜껑은 시계방향으로 돌릴 수 있고, 한 번 돌릴 때 숫자 시계방향으로 회전
 * 
 * 각 변에는 동일한 개수의 숫자, 시계방향 순으로 높은 자리 숫자에 해당하며 하나의 수를 나타낸다.
 * 보물상자에는 자물쇠가 걸려져있고, 이 자물쇠의 비밀번호는 보물상자에 적힌 숫자로 만들 수 있는 모든 수 중, K번째로 큰 수 10진수로 만든 수이다.
 * 
 * N개의 숫자가 입력으로 주어졌을때, 보물상자의 비밀번호를 출력하는 프로그램을 만들어보라.
 * 
 * [풀이]
 * 회전 할 때 => 넣은 순서대로 앞에서 꺼내서 뒤에 넣기 -> deque
 * 중복 안되고 큰 값으로 정렬 -> TreeSet
 */

public class Solution { // 보물상자 비밀번호

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			int N = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());

			ArrayDeque<Character> arr = new ArrayDeque<Character>();
			TreeSet<Integer> treeSet = new TreeSet<Integer>();

			String line = br.readLine();
			for (int i = 0; i < N; i++) {
				arr.add(line.charAt(i));
			}

			int l = N / 4;

			for (int t = 0; t < l; t++) {

				for (int i = 0; i < N; i += l) {
					String str = "";

					for (int j = i; j < i + l; j++) {
						char c = arr.pollFirst();
						str += c;
						arr.addLast(c);
					}

					int num = Integer.parseInt(str, 16);

					treeSet.add(num);
				}

				// 회전
				arr.addLast(arr.pollFirst());
			}

			int answer = 0;

			for (int i = 1; i <= K; i++) {
				answer = treeSet.pollLast();
			}

			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.println(sb);
	}
}
