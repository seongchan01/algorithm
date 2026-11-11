class Solution {

	static boolean[][] visitRed;
	static boolean[][] visitBlue;
	static int answer;
	static int[] dx = {0, 0, 1, -1};
	static int[] dy = {1, -1, 0, 0};
	static int N, M;
	static int redFinishX, redFinishY;
	static int blueFinishX, blueFinishY;

	public int solution(int[][] maze) {

		answer = Integer.MAX_VALUE;

		N = maze.length;
		M = maze[0].length;

		int redStartX = 0;
		int redStartY = 0;

		int blueStartX = 0;
		int blueStartY = 0;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {

				if (maze[i][j] == 1) {
					redStartX = i;
					redStartY = j;
				}

				if (maze[i][j] == 2) {
					blueStartX = i;
					blueStartY = j;
				}

				if (maze[i][j] == 3) {
					redFinishX = i;
					redFinishY = j;
				}

				if (maze[i][j] == 4) {
					blueFinishX = i;
					blueFinishY = j;
				}
			}
		}

		visitRed = new boolean[N][M];
		visitBlue = new boolean[N][M];

		visitRed[redStartX][redStartY] = true;
		visitBlue[blueStartX][blueStartY] = true;

		dfs(maze, redStartX, redStartY, blueStartX, blueStartY, 0);

		if (answer == Integer.MAX_VALUE) {
            return 0;
        } else {
            return answer;
        }
	}

	static void dfs(int[][] maze, int rX, int rY, int bX, int bY, int turn) {

		boolean redFinish = rX == redFinishX && rY == redFinishY;
		boolean blueFinish = bX == blueFinishX && bY == blueFinishY;

		// 두 수레 모두 도착
		if (redFinish && blueFinish) {
			answer = Math.min(answer, turn);
			return;
		}

		if (turn >= answer) {
			return;
		}

		// red
		for (int i = 0; i < 4; i++) {

			int nrX = rX;
			int nrY = rY;

			if (!redFinish) {

				nrX = rX + dx[i];
				nrY = rY + dy[i];

				if (nrX < 0 || nrY < 0 || nrX >= N || nrY >= M || maze[nrX][nrY] == 5) {
					continue;
				}

				if (visitRed[nrX][nrY]) {
					continue;
				}
			}

			// blue
			for (int j = 0; j < 4; j++) {

				int nbX = bX;
				int nbY = bY;

				if (!blueFinish) {

					nbX = bX + dx[j];
					nbY = bY + dy[j];

					if (nbX < 0 || nbY < 0 || nbX >= N || nbY >= M || maze[nbX][nbY] == 5) {
						continue;
					}

					if (visitBlue[nbX][nbY]) {
						continue;
					}
				}

				if (nrX == nbX && nrY == nbY) {
					continue;
				}

				if (nrX == bX && nrY == bY && nbX == rX && nbY == rY) {
					continue;
				}

				if (!redFinish) {
					visitRed[nrX][nrY] = true;
				}

				if (!blueFinish) {
					visitBlue[nbX][nbY] = true;
				}

				dfs(maze, nrX, nrY, nbX, nbY, turn + 1);

				// 백트래킹
				if (!redFinish) {
					visitRed[nrX][nrY] = false;
				}

				if (!blueFinish) {
					visitBlue[nbX][nbY] = false;
				}
			}
		}
	}
}
