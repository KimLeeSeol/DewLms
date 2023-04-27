package com.kdew.dewlms.course.service;

import com.kdew.dewlms.course.dto.CourseDto;
import com.kdew.dewlms.course.entity.Course;
import com.kdew.dewlms.course.entity.TakeCourse;
import com.kdew.dewlms.course.mapper.CourseMapper;
import com.kdew.dewlms.course.model.CourseInput;
import com.kdew.dewlms.course.model.CourseParam;
import com.kdew.dewlms.course.model.ServiceResult;
import com.kdew.dewlms.course.model.TakeCourseInput;
import com.kdew.dewlms.course.repository.CourseRepository;
import com.kdew.dewlms.course.repository.TakeCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final TakeCourseRepository takeCourseRepository;

    // 종료일 받기
    private LocalDate getLocalDate(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd"); // 이런 패턴만 받을 것임
        try {
            return LocalDate.parse(value, formatter);
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public boolean add(CourseInput parameter) {

        LocalDate saleEndDt = getLocalDate(parameter.getSaleEndDtText());

        // 받은 데이터 저장
        Course course = Course.builder()
                .categoryId(parameter.getCategoryId())
                .subject(parameter.getSubject())
                .keyword(parameter.getKeyword())
                .summary(parameter.getSummary())
                .contents(parameter.getContents())
                .price(parameter.getPrice())
                .salePrice(parameter.getSalePrice())
                .saleEndDt(saleEndDt)
                .regDt(LocalDateTime.now())
                .filename(parameter.getFilename())
                .urlFilename(parameter.getUrlFilename())
                .build();
        courseRepository.save(course);

        return true;
    }

    @Override
    public boolean set(CourseInput parameter) {
        Optional<Course> optionalCourse = courseRepository.findById(parameter.getId());
        if(!optionalCourse.isPresent()) {
            // 수정할 데이터가 없을 때
            return false;
        }

        LocalDate saleEndDt = getLocalDate(parameter.getSaleEndDtText());
        Course course = optionalCourse.get();

        course.setCategoryId(parameter.getCategoryId());
        course.setSubject(parameter.getSubject());
        course.setKeyword(parameter.getKeyword());
        course.setSummary(parameter.getSummary());
        course.setContents(parameter.getContents());
        course.setPrice(parameter.getPrice());
        course.setSalePrice(parameter.getSalePrice());
        course.setSaleEndDt(saleEndDt);
        course.setUptDt(LocalDateTime.now());
        course.setFilename(parameter.getFilename());
        course.setUrlFilename(parameter.getUrlFilename());
        courseRepository.save(course);

        return true;
    }

    @Override
    public List<CourseDto> list(CourseParam parameter) {

        long totalCount = courseMapper.selectListCount(parameter);

        List<CourseDto> list = courseMapper.selectList(parameter);
        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            // 리스트가 비어있지 않으면
            for (CourseDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }

        return list;
    }

    @Override
    public CourseDto getById(long id) {

        return courseRepository.findById(id).map(CourseDto::of).orElse(null);
    }

    @Override
    public boolean del(String idList) {

        if (idList != null && idList.length() > 0) {
            String[] ids = idList.split(",");
            for(String x : ids) {
                long id = 0L;
                try{
                    id = Long.parseLong(x);
                } catch (Exception e) {
                }

                if (id > 0) {
                    courseRepository.deleteById(id);
                }
            }
        }
        return true;
    }

    @Override
    public List<CourseDto> frontList(CourseParam parameter) {

        if (parameter.getCategoryId() < 1) {
            // 전체를 다 가져온 것이기 때문
            List<Course> courseList = courseRepository.findAll();
            return CourseDto.of(courseList);
        }

        Optional<List<Course>> optionalCourse = courseRepository.findByCategoryId(parameter.getCategoryId());
        if (optionalCourse.isPresent()) {
            return CourseDto.of(optionalCourse.get());
        }
        return null;

    }

    @Override
    public CourseDto frontDetail(long id) {

        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            return CourseDto.of(optionalCourse.get());
        }
        return null;
    }

    @Override
    public ServiceResult req(TakeCourseInput parameter) {

        ServiceResult result = new ServiceResult();

        Optional<Course> optionalCourse = courseRepository.findById(parameter.getCourseId());
        if (!optionalCourse.isPresent()) {
            result.setResult(false);
            result.setMessage("강좌 정보가 존재하지 않습니다.");
            return result;
        }
        Course course = optionalCourse.get();

        // 이미 신청 정보가 있는지 확인 (아이디와 강좌 아이디를 통해!)
        String[] statusList = {TakeCourse.STATUS_REQ, TakeCourse.STATUS_COMPLETE};
        long count = takeCourseRepository.countByCourseIdAndUserIdAndStatusIn(course.getId()
        , parameter.getUserId(), List.of(statusList));

        if(count > 0) {
            result.setResult(false);
            result.setMessage("이미 신청한 강좌 정보가 존재합니다.");
            return result;

        }

        TakeCourse takeCourse = TakeCourse.builder()
                .courseId(course.getId())
                .userId(parameter.getUserId())
                .payPrice(course.getSalePrice())
                .regDt(LocalDateTime.now())
                .status(TakeCourse.STATUS_REQ)
                .build();

        takeCourseRepository.save(takeCourse);

        result.setResult(true);
        return result;
    }

    @Override
    public List<CourseDto> listAll() {

        List<Course> courseList = courseRepository.findAll();

        return CourseDto.of(courseList);

    }
}
