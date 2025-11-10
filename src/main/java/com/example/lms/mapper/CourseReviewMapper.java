package com.example.lms.mapper;

import com.example.lms.dto.CourseReviewResponse;
import com.example.lms.dto.CreateCourseReviewRequest;
import com.example.lms.dto.UpdateCourseReviewRequest;
import com.example.lms.model.Course;
import com.example.lms.model.CourseReview;
import com.example.lms.model.User;
import org.springframework.stereotype.Component;

@Component
public class CourseReviewMapper {

    public CourseReview toEntity(CreateCourseReviewRequest request, Course course, User student) {
        CourseReview review = new CourseReview();
        review.setCourse(course);
        review.setStudent(student);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        return review;
    }

    public CourseReview toEntity(UpdateCourseReviewRequest request, Course course, User student, CourseReview existingReview) {
        existingReview.setCourse(course);
        existingReview.setStudent(student);
        existingReview.setRating(request.getRating());
        existingReview.setComment(request.getComment());
        return existingReview;
    }

    public CourseReviewResponse toResponse(CourseReview review) {
        CourseReviewResponse response = new CourseReviewResponse();
        response.setId(review.getId());
        response.setCourseId(review.getCourse().getId());
        response.setCourseTitle(review.getCourse().getTitle());
        response.setStudentId(review.getStudent().getId());
        response.setStudentName(review.getStudent().getName());
        response.setRating(review.getRating());
        response.setComment(review.getComment());
        response.setCreatedAtReview(review.getCreatedAtReview());
        return response;
    }
}
