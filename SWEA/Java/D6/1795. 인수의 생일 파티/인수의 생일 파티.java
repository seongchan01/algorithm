import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * [문제 분석]
 * 인수가 사는 마을에는 N개의 집이 있고, 각 집에는 한 명만 산다.
 * N개의 집이 정점 - 집이 이어진 도로는 단방향 간선, 이동하는데 걸리는 시간 정해져있음(가중치)
 * 도로는 모든 집에 이동이 가능함
 * 집에 1	번부터 N번까지 번호 붙어있고, 인수의 집 X번
 * 마을 사람들이 인수의 생일 축하해주기 위해 X번 집으로 모임
 * 각 사람들은 집에서 X번 집으로 모이기 위해 최단시간으로 이동
 * 도로는 단방향이기때문에 오고 갈 때 다를 것	
 * X번 집으로 오고 가는데 드는 시간 중에서 가장 오래 걸리는 집의 시간을 구하라
 * 
 * [풀이]
 * 도로가 모든 집들간에서 이동이 가능 -> 다익스트라로 최단거리 구함
 * 오고 갈 때 다르기 때문에 두 번 해야함.
 * 두번 하고 두 다익스트라 나온 값 합쳐서 최댓값
 */

public class Solution { // D6. 인수의 생일 파티

	static int N, M, X;
	static ArrayList<int[]>[] graph;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			N = Integer.parseInt(st.nextToken()); // 집 1~N개
			M = Integer.parseInt(st.nextToken());
			X = Integer.parseInt(st.nextToken()); // X번째 집

			graph = new ArrayList[N + 1]; // 간선 저장할 graph

			for (int i = 1; i <= N; i++) {
				graph[i] = new ArrayList<>();
			}

			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine()); // x번 집에서 y번 집으로 가는데 걸리는 시간이 c (단방향)
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());

				graph[x].add(new int[] { y, c }); // x->y 시간c
			}

			int[] go = new int[N + 1];
			int[] reback = new int[N + 1];

			// 집 -> X
			for (int i = 1; i <= N; i++) {
				go[i] = dijkstra(i, X, graph);
			}

			// X -> 집
			for (int i = 1; i <= N; i++) {
				reback[i] = dijkstra(X, i, graph);
			}

			//왕복계산
			int[] answer = new int[N + 1];
			for (int i = 1; i <= N; i++) {
				answer[i] = go[i] + reback[i];
			}
			
			Arrays.sort(answer);

			sb.append("#").append(tc).append(" ").append(answer[answer.length - 1]).append("\n");

		}

		System.out.println(sb);
	}

	//다익스트라
	static int dijkstra(int start, int finish, ArrayList<int[]>[] graph) {

		int[] dist = new int[N + 1];
		Arrays.fill(dist, Integer.MAX_VALUE);

		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));

		dist[start] = 0;
		pq.offer(new int[] { start, 0 });

		while (!pq.isEmpty()) {
			int[] now = pq.poll();

			int nowHome = now[0];
			int cost = now[1];

			// 저장해온거보다 다음이 크면 X
			if (dist[nowHome] < cost)
				continue;

			// 도착할때 최소값반환
			if (nowHome == finish)
				return cost;

			for (int[] next : graph[nowHome]) {
				int nextHome = next[0];
				int nextCost = cost + next[1];

				if (dist[nextHome] > nextCost) {
					dist[nextHome] = nextCost;
					pq.offer(new int[] { nextHome, nextCost });
				}
			}
		}

		return 0;
	}
}
