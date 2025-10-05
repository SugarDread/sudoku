import axios from 'axios';

const API_BASE_URL = "http://localhost:8080/api";

export const sudokuApi = {

    getNewBoard: async (difficulty) => {
        const response = await axios.get(`${API_BASE_URL}/board/${difficulty}/random`);

        return response.data;
    },

    validateMove: async (id, position, value) => {
        
        const response = await axios.post(`${API_BASE_URL}/board/validate?id=${id}&position=${position}&value=${value}`);
        return response.data;
    }
}